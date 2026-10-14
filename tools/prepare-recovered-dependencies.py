#!/usr/bin/env python3
"""Restore checksum-pinned recovered APIs from the retained cloud recovery cache.

The cache contains exact upstream source builds and verified bundled artifacts.
This installer neither downloads substitutions nor starts production services.
"""
from pathlib import Path
import hashlib,json,subprocess,zipfile
root=Path(__file__).resolve().parent.parent
for row in json.loads((root/'tools/recovered-build-dependencies.json').read_text()):
    file=Path(row['path'])
    if not file.is_file():raise SystemExit('Required recovered artifact is missing: '+str(file))
    if hashlib.sha256(file.read_bytes()).hexdigest()!=row['sha256']:raise SystemExit('Recovered artifact checksum mismatch: '+str(file))
    with zipfile.ZipFile(file) as archive:
        if archive.testzip():raise SystemExit('Corrupt recovered artifact: '+str(file))
    extra=[]
    if row.get('pom'):
        pom=Path(row['pom'])
        if hashlib.sha256(pom.read_bytes()).hexdigest()!=row['pom_sha256']:raise SystemExit('Recovered dependency metadata checksum mismatch')
        extra=['-DpomFile='+str(pom)]
    subprocess.run(['/workspace/tooling/astro-mvn','-N','org.apache.maven.plugins:maven-install-plugin:3.1.3:install-file','-Dfile='+str(file),'-DgroupId='+row['group'],'-DartifactId='+row['artifact'],'-Dversion='+row['version'],'-Dpackaging=jar','-DgeneratePom=true']+extra,cwd='/workspace/tooling',check=True)
print('Recovered build dependencies verified and installed, including complete ACF.')
