/*
 * Decompiled with CFR 0.150.
 */
package com.avaje.ebeaninternal.server.ddl;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.AddForeignKeysVisitor;
import com.avaje.ebeaninternal.server.ddl.BeanVisitor;
import com.avaje.ebeaninternal.server.ddl.CreateSequenceVisitor;
import com.avaje.ebeaninternal.server.ddl.CreateTableVisitor;
import com.avaje.ebeaninternal.server.ddl.DdlGenContext;
import com.avaje.ebeaninternal.server.ddl.DropSequenceVisitor;
import com.avaje.ebeaninternal.server.ddl.DropTableVisitor;
import com.avaje.ebeaninternal.server.ddl.VisitorUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DdlGenerator {
    private static final Logger logger = Logger.getLogger(DdlGenerator.class.getName());
    private final SpiEbeanServer server;
    private final DatabasePlatform dbPlatform;
    private PrintStream out = System.out;
    private int summaryLength = 80;
    private boolean debug = true;
    private boolean generateDdl;
    private boolean runDdl;
    private String dropContent;
    private String createContent;
    private NamingConvention namingConvention;

    public DdlGenerator(SpiEbeanServer server, DatabasePlatform dbPlatform, ServerConfig serverConfig) {
        this.server = server;
        this.dbPlatform = dbPlatform;
        this.generateDdl = serverConfig.isDdlGenerate();
        this.runDdl = serverConfig.isDdlRun();
        this.namingConvention = serverConfig.getNamingConvention();
    }

    public void execute(boolean online) {
        this.generateDdl();
        if (online) {
            this.runDdl();
        }
    }

    public void generateDdl() {
        if (this.generateDdl) {
            this.writeDrop(this.getDropFileName());
            this.writeCreate(this.getCreateFileName());
        }
    }

    public void runDdl() {
        if (this.runDdl) {
            try {
                if (this.dropContent == null) {
                    this.dropContent = this.readFile(this.getDropFileName());
                }
                if (this.createContent == null) {
                    this.createContent = this.readFile(this.getCreateFileName());
                }
                this.runScript(true, this.dropContent);
                this.runScript(false, this.createContent);
            }
            catch (IOException e) {
                String msg = "Error reading drop/create script from file system";
                throw new RuntimeException(msg, e);
            }
        }
    }

    protected void writeDrop(String dropFile) {
        try {
            String c = this.generateDropDdl();
            this.writeFile(dropFile, c);
        }
        catch (IOException e) {
            String msg = "Error generating Drop DDL";
            throw new PersistenceException(msg, e);
        }
    }

    protected void writeCreate(String createFile) {
        try {
            String c = this.generateCreateDdl();
            this.writeFile(createFile, c);
        }
        catch (IOException e) {
            String msg = "Error generating Create DDL";
            throw new PersistenceException(msg, e);
        }
    }

    public String generateDropDdl() {
        DdlGenContext ctx = this.createContext();
        DropTableVisitor drop = new DropTableVisitor(ctx);
        VisitorUtil.visit(this.server, (BeanVisitor)drop);
        DropSequenceVisitor dropSequence = new DropSequenceVisitor(ctx);
        VisitorUtil.visit(this.server, (BeanVisitor)dropSequence);
        ctx.flush();
        this.dropContent = ctx.getContent();
        return this.dropContent;
    }

    public String generateCreateDdl() {
        DdlGenContext ctx = this.createContext();
        CreateTableVisitor create = new CreateTableVisitor(ctx);
        VisitorUtil.visit(this.server, (BeanVisitor)create);
        CreateSequenceVisitor createSequence = new CreateSequenceVisitor(ctx);
        VisitorUtil.visit(this.server, (BeanVisitor)createSequence);
        AddForeignKeysVisitor fkeys = new AddForeignKeysVisitor(ctx);
        VisitorUtil.visit(this.server, (BeanVisitor)fkeys);
        ctx.flush();
        this.createContent = ctx.getContent();
        return this.createContent;
    }

    protected String getDropFileName() {
        return this.server.getName() + "-drop.sql";
    }

    protected String getCreateFileName() {
        return this.server.getName() + "-create.sql";
    }

    protected DdlGenContext createContext() {
        return new DdlGenContext(this.dbPlatform, this.namingConvention);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void writeFile(String fileName, String fileContent) throws IOException {
        File f = new File(fileName);
        FileWriter fw = new FileWriter(f);
        try {
            fw.write(fileContent);
            fw.flush();
        }
        finally {
            fw.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String readFile(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        FileReader fr = new FileReader(f);
        LineNumberReader lr = new LineNumberReader(fr);
        try {
            String s = null;
            while ((s = lr.readLine()) != null) {
                buf.append(s).append("\n");
            }
        }
        finally {
            lr.close();
        }
        return buf.toString();
    }

    public void runScript(boolean expectErrors, String content) {
        StringReader sr = new StringReader(content);
        List<String> statements = this.parseStatements(sr);
        Transaction t = this.server.createTransaction();
        try {
            Connection connection = t.getConnection();
            this.out.println("runScript");
            this.out.flush();
            this.runStatements(expectErrors, statements, connection);
            this.out.println("... end of script");
            this.out.flush();
            t.commit();
        }
        catch (Exception e) {
            String msg = "Error: " + e.getMessage();
            throw new PersistenceException(msg, e);
        }
        finally {
            t.end();
        }
    }

    private void runStatements(boolean expectErrors, List<String> statements, Connection c) {
        for (int i = 0; i < statements.size(); ++i) {
            String xOfy = i + 1 + " of " + statements.size();
            this.runStatement(expectErrors, xOfy, statements.get(i), c);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void runStatement(boolean expectErrors, String oneOf, String stmt, Connection c) {
        block17: {
            Statement pstmt = null;
            try {
                stmt = stmt.trim();
                if (stmt.endsWith(";")) {
                    stmt = stmt.substring(0, stmt.length() - 1);
                } else if (stmt.endsWith("/")) {
                    stmt = stmt.substring(0, stmt.length() - 1);
                }
                if (this.debug) {
                    this.out.println("executing " + oneOf + " " + this.getSummary(stmt));
                    this.out.flush();
                }
                pstmt = c.prepareStatement(stmt);
                pstmt.execute();
            }
            catch (Exception e) {
                if (expectErrors) {
                    this.out.println(" ... ignoring error executing " + this.getSummary(stmt) + "  error: " + e.getMessage());
                    e.printStackTrace();
                    this.out.flush();
                    break block17;
                }
                String msg = "Error executing stmt[" + stmt + "] error[" + e.getMessage() + "]";
                throw new RuntimeException(msg, e);
            }
            finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    }
                    catch (SQLException e) {
                        logger.log(Level.SEVERE, "Error closing pstmt", e);
                    }
                }
            }
        }
    }

    protected List<String> parseStatements(StringReader reader) {
        try {
            String s;
            BufferedReader br = new BufferedReader(reader);
            ArrayList<String> statements = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                int semiPos = (s = s.trim()).indexOf(59);
                if (semiPos == -1) {
                    sb.append(s).append(" ");
                    continue;
                }
                if (semiPos == s.length() - 1) {
                    sb.append(s);
                    statements.add(sb.toString().trim());
                    sb = new StringBuilder();
                    continue;
                }
                String preSemi = s.substring(0, semiPos);
                sb.append(preSemi);
                statements.add(sb.toString().trim());
                sb = new StringBuilder();
                sb.append(s.substring(semiPos + 1));
            }
            return statements;
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }

    private String getSummary(String s) {
        if (s.length() > this.summaryLength) {
            return s.substring(0, this.summaryLength).trim() + "...";
        }
        return s;
    }
}

