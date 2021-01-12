/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FileAlreadyExistsException;
import kotlin.io.FilePathComponents;
import kotlin.io.FileSystemException;
import kotlin.io.FilesKt;
import kotlin.io.FilesKt__FileTreeWalkKt;
import kotlin.io.FilesKt__UtilsKt;
import kotlin.io.NoSuchFileException;
import kotlin.io.OnErrorAction;
import kotlin.io.TerminateException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=5, xi=1, d1={"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002\u00a2\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0004\u00a8\u0006+"}, d2={"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, xs="kotlin/io/FilesKt")
class FilesKt__UtilsKt
extends FilesKt__FileTreeWalkKt {
    @NotNull
    public static final File createTempDir(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File dir = File.createTempFile(prefix, suffix, directory);
        dir.delete();
        if (dir.mkdir()) {
            File file = dir;
            Intrinsics.checkExpressionValueIsNotNull(file, "dir");
            return file;
        }
        throw (Throwable)new IOException("Unable to create temporary directory " + dir + '.');
    }

    @NotNull
    public static /* bridge */ /* synthetic */ File createTempDir$default(String string, String string2, File file, int n, Object object) {
        if ((n & 1) != 0) {
            string = "tmp";
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempDir(string, string2, file);
    }

    @NotNull
    public static final File createTempFile(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        File file = File.createTempFile(prefix, suffix, directory);
        Intrinsics.checkExpressionValueIsNotNull(file, "File.createTempFile(prefix, suffix, directory)");
        return file;
    }

    @NotNull
    public static /* bridge */ /* synthetic */ File createTempFile$default(String string, String string2, File file, int n, Object object) {
        if ((n & 1) != 0) {
            string = "tmp";
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            file = null;
        }
        return FilesKt.createTempFile(string, string2, file);
    }

    @NotNull
    public static final String getExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String string = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(string, "name");
        return StringsKt.substringAfterLast(string, '.', "");
    }

    @NotNull
    public static final String getInvariantSeparatorsPath(@NotNull File $receiver) {
        String string;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (File.separatorChar != '/') {
            String string2 = $receiver.getPath();
            Intrinsics.checkExpressionValueIsNotNull(string2, "path");
            string = StringsKt.replace$default(string2, File.separatorChar, '/', false, 4, null);
        } else {
            String string3 = $receiver.getPath();
            string = string3;
            Intrinsics.checkExpressionValueIsNotNull(string3, "path");
        }
        return string;
    }

    @NotNull
    public static final String getNameWithoutExtension(@NotNull File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String string = $receiver.getName();
        Intrinsics.checkExpressionValueIsNotNull(string, "name");
        return StringsKt.substringBeforeLast$default(string, ".", null, 2, null);
    }

    @NotNull
    public static final String toRelativeString(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String string = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (string == null) {
            throw (Throwable)new IllegalArgumentException("this and base files have different roots: " + $receiver + " and " + base + '.');
        }
        return string;
    }

    @NotNull
    public static final File relativeTo(@NotNull File $receiver, @NotNull File base) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        return new File(FilesKt.toRelativeString($receiver, base));
    }

    @NotNull
    public static final File relativeToOrSelf(@NotNull File $receiver, @NotNull File base) {
        File file;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String string = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (string != null) {
            String string2;
            String p1 = string2 = string;
            file = new File(p1);
        } else {
            file = $receiver;
        }
        return file;
    }

    @Nullable
    public static final File relativeToOrNull(@NotNull File $receiver, @NotNull File base) {
        File file;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(base, "base");
        String string = FilesKt__UtilsKt.toRelativeStringOrNull$FilesKt__UtilsKt($receiver, base);
        if (string != null) {
            String string2;
            String p1 = string2 = string;
            file = new File(p1);
        } else {
            file = null;
        }
        return file;
    }

    /*
     * WARNING - void declaration
     */
    private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File $receiver, File base) {
        int i;
        File file;
        FilePathComponents thisComponents = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(FilesKt.toComponents($receiver));
        FilePathComponents baseComponents = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt(FilesKt.toComponents(base));
        if (Intrinsics.areEqual(thisComponents.getRoot(), baseComponents.getRoot()) ^ true) {
            return null;
        }
        int baseCount = baseComponents.getSize();
        int thisCount = thisComponents.getSize();
        File $receiver22 = file = $receiver;
        int n = thisCount;
        int n2 = baseCount;
        int maxSameCount = Math.min(n, n2);
        for (i = 0; i < maxSameCount && Intrinsics.areEqual(thisComponents.getSegments().get(i), baseComponents.getSegments().get(i)); ++i) {
        }
        int sameCount = i;
        StringBuilder res = new StringBuilder();
        int $receiver22 = baseCount - 1;
        int n3 = sameCount;
        if ($receiver22 >= n3) {
            void i2;
            do {
                if (Intrinsics.areEqual(baseComponents.getSegments().get((int)(--i2)).getName(), "..")) {
                    return null;
                }
                res.append("..");
                if (i2 == sameCount) continue;
                res.append(File.separatorChar);
            } while (i2 != n3);
        }
        if (sameCount < thisCount) {
            if (sameCount < baseCount) {
                res.append(File.separatorChar);
            }
            Iterable iterable = CollectionsKt.drop((Iterable)thisComponents.getSegments(), sameCount);
            Appendable appendable = res;
            String string = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(string, "File.separator");
            CollectionsKt.joinTo$default(iterable, appendable, string, null, null, 0, null, null, 124, null);
        }
        return res.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public static final File copyTo(@NotNull File $receiver, @NotNull File target, boolean overwrite, int bufferSize) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(target, "target");
        if (!$receiver.exists()) {
            throw (Throwable)new NoSuchFileException($receiver, null, "The source file doesn't exist.", 2, null);
        }
        if (target.exists()) {
            boolean stillExists;
            boolean bl = !overwrite ? true : (stillExists = !target.delete());
            if (stillExists) {
                throw (Throwable)new FileAlreadyExistsException($receiver, target, "The destination file already exists.");
            }
        }
        if ($receiver.isDirectory()) {
            if (!target.mkdirs()) {
                throw (Throwable)new FileSystemException($receiver, target, "Failed to create target directory.");
            }
        } else {
            File file = target.getParentFile();
            if (file != null) {
                file.mkdirs();
            }
            Object object = $receiver;
            object = new FileInputStream((File)object);
            Throwable throwable = null;
            try {
                long l;
                FileInputStream input = (FileInputStream)object;
                Object object2 = target;
                object2 = new FileOutputStream((File)object2);
                Throwable throwable2 = null;
                try {
                    FileOutputStream output = (FileOutputStream)object2;
                    l = ByteStreamsKt.copyTo(input, output, bufferSize);
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)object2, throwable2);
                }
                long l2 = l;
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                CloseableKt.closeFinally((Closeable)object, throwable);
            }
        }
        return target;
    }

    @NotNull
    public static /* bridge */ /* synthetic */ File copyTo$default(File file, File file2, boolean bl, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = 8192;
        }
        return FilesKt.copyTo(file, file2, bl, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final boolean copyRecursively(@NotNull File $receiver, @NotNull File target, boolean overwrite, @NotNull Function2<? super File, ? super IOException, ? extends OnErrorAction> onError) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(target, "target");
        Intrinsics.checkParameterIsNotNull(onError, "onError");
        if (!$receiver.exists()) {
            if (onError.invoke($receiver, new NoSuchFileException($receiver, null, "The source file doesn't exist.", 2, null)) == OnErrorAction.TERMINATE) return false;
            return true;
        }
        try {
            Iterator<File> iterator2 = FilesKt.walkTopDown($receiver).onFail((Function2<? super File, ? super IOException, Unit>)new Function2<File, IOException, Unit>(onError){
                final /* synthetic */ Function2 $onError;

                public final void invoke(@NotNull File f, @NotNull IOException e) {
                    Intrinsics.checkParameterIsNotNull(f, "f");
                    Intrinsics.checkParameterIsNotNull(e, "e");
                    if ((OnErrorAction)((Object)this.$onError.invoke(f, e)) == OnErrorAction.TERMINATE) {
                        throw (Throwable)new TerminateException(f);
                    }
                }
                {
                    this.$onError = function2;
                    super(2);
                }
            }).iterator();
            while (iterator2.hasNext()) {
                File src = iterator2.next();
                if (!src.exists()) {
                    if (onError.invoke(src, new NoSuchFileException(src, null, "The source file doesn't exist.", 2, null)) != OnErrorAction.TERMINATE) continue;
                    return false;
                }
                String relPath = FilesKt.toRelativeString(src, $receiver);
                File dstFile = new File(target, relPath);
                if (!(!dstFile.exists() || src.isDirectory() && dstFile.isDirectory())) {
                    boolean stillExists;
                    boolean bl = !overwrite ? true : (dstFile.isDirectory() ? !FilesKt.deleteRecursively(dstFile) : (stillExists = !dstFile.delete()));
                    if (stillExists) {
                        if (onError.invoke(dstFile, new FileAlreadyExistsException(src, dstFile, "The destination file already exists.")) != OnErrorAction.TERMINATE) continue;
                        return false;
                    }
                }
                if (src.isDirectory()) {
                    dstFile.mkdirs();
                    continue;
                }
                if (FilesKt.copyTo$default(src, dstFile, overwrite, 0, 4, null).length() != src.length() && onError.invoke(src, new IOException("Source file wasn't copied completely, length of destination file differs.")) == OnErrorAction.TERMINATE) return false;
            }
            return true;
        }
        catch (TerminateException e) {
            return false;
        }
    }

    public static /* bridge */ /* synthetic */ boolean copyRecursively$default(File file, File file2, boolean bl, Function2 function2, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            function2 = copyRecursively.1.INSTANCE;
        }
        return FilesKt.copyRecursively(file, file2, bl, function2);
    }

    /*
     * WARNING - void declaration
     */
    public static final boolean deleteRecursively(@NotNull File $receiver) {
        void var3_3;
        void $receiver$iv;
        boolean initial$iv;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Sequence sequence = FilesKt.walkBottomUp($receiver);
        boolean accumulator$iv = initial$iv = true;
        for (Object element$iv : $receiver$iv) {
            void it;
            File file = (File)element$iv;
            boolean res = accumulator$iv;
            accumulator$iv = (it.delete() || !it.exists()) && res;
        }
        return (boolean)var3_3;
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        FilePathComponents components = FilesKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (Intrinsics.areEqual(components.getRoot(), otherComponents.getRoot()) ^ true) {
            return false;
        }
        return components.getSize() < otherComponents.getSize() ? false : ((Object)components.getSegments().subList(0, otherComponents.getSize())).equals(otherComponents.getSegments());
    }

    public static final boolean startsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return FilesKt.startsWith($receiver, new File(other));
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull File other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        FilePathComponents components = FilesKt.toComponents($receiver);
        FilePathComponents otherComponents = FilesKt.toComponents(other);
        if (otherComponents.isRooted()) {
            return Intrinsics.areEqual($receiver, other);
        }
        int shift = components.getSize() - otherComponents.getSize();
        return shift < 0 ? false : ((Object)components.getSegments().subList(shift, components.getSize())).equals(otherComponents.getSegments());
    }

    public static final boolean endsWith(@NotNull File $receiver, @NotNull String other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        return FilesKt.endsWith($receiver, new File(other));
    }

    @NotNull
    public static final File normalize(@NotNull File $receiver) {
        FilePathComponents filePathComponents;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        FilePathComponents $receiver2 = filePathComponents = FilesKt.toComponents($receiver);
        File file = $receiver2.getRoot();
        Iterable iterable = FilesKt__UtilsKt.normalize$FilesKt__UtilsKt($receiver2.getSegments());
        String string = File.separator;
        Intrinsics.checkExpressionValueIsNotNull(string, "File.separator");
        return FilesKt.resolve(file, CollectionsKt.joinToString$default(iterable, string, null, null, 0, null, null, 62, null));
    }

    private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents $receiver) {
        return new FilePathComponents($receiver.getRoot(), FilesKt__UtilsKt.normalize$FilesKt__UtilsKt($receiver.getSegments()));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static final List<File> normalize$FilesKt__UtilsKt(@NotNull List<? extends File> $receiver) {
        list = new ArrayList<E>($receiver.size());
        var3_2 = $receiver.iterator();
        block4: while (var3_2.hasNext() != false) {
            var2_3 = var3_2.next();
            v0 = var4_4 = var2_3.getName();
            if (v0 != null) {
                switch (v0.hashCode()) {
                    case 1472: {
                        if (!var4_4.equals("..")) ** break;
                        break;
                    }
                    case 46: {
                        if (!var4_4.equals(".")) ** break;
                        continue block4;
                    }
                }
                if (!list.isEmpty() && Intrinsics.areEqual(((File)CollectionsKt.last(list)).getName(), "..") ^ true) {
                    list.remove(list.size() - 1);
                    continue;
                }
                list.add(var2_3);
                continue;
            }
            list.add(var2_3);
        }
        return list;
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull File relative) {
        String baseName;
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        if (FilesKt.isRooted(relative)) {
            return relative;
        }
        String string = baseName = $receiver.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "baseName");
        CharSequence charSequence = string;
        return charSequence.length() == 0 || StringsKt.endsWith$default((CharSequence)baseName, File.separatorChar, false, 2, null) ? new File(baseName + relative) : new File(baseName + File.separatorChar + relative);
    }

    @NotNull
    public static final File resolve(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolve($receiver, new File(relative));
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull File relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        FilePathComponents components = FilesKt.toComponents($receiver);
        File parentSubPath = components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1);
        return FilesKt.resolve(FilesKt.resolve(components.getRoot(), parentSubPath), relative);
    }

    @NotNull
    public static final File resolveSibling(@NotNull File $receiver, @NotNull String relative) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(relative, "relative");
        return FilesKt.resolveSibling($receiver, new File(relative));
    }
}

