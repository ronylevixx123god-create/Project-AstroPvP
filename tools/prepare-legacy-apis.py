#!/usr/bin/env python3
"""Reinstall verified historical APIs; never substitute modern server APIs.

Requires the Java 8/Maven wrapper prepared by the cloud installation script.
The manifest pins original bundled files and one official ProtocolLib release.
Complete ACF and exact recovered build dependencies are restored by
prepare-recovered-dependencies.py after these original API extracts.
"""
from pathlib import Path
import argparse,hashlib,json,subprocess,urllib.request,zipfile
parser=argparse.ArgumentParser();parser.add_argument('--maven',default='/workspace/tooling/astro-mvn');parser.add_argument('--output',default='/workspace/tooling/recovered-api');args=parser.parse_args()
repo=Path(__file__).resolve().parent.parent;out=Path(args.output);out.mkdir(parents=True,exist_ok=True)
for row in json.loads((repo/'tools/legacy-api-manifest.json').read_text()):
 target=out/(row['artifact']+'-'+row['version']+'.jar')
 if 'source' in row:source=repo/row['source']
 else:
  source=out/'official-ProtocolLib-4.5.0.jar'
  if not source.exists():
   with urllib.request.urlopen(row['url'],timeout=60) as response:source.write_bytes(response.read())
 if hashlib.sha256(source.read_bytes()).hexdigest()!=row['sha256']:raise SystemExit('Checksum mismatch: '+source.name)
 with zipfile.ZipFile(source) as original:
  if original.testzip():raise SystemExit('Corrupt archive: '+source.name)
  prefixes=row.get('prefixes')
  if prefixes:
   with zipfile.ZipFile(target,'w',zipfile.ZIP_DEFLATED) as extracted:
    for name in original.namelist():
     if any(name.startswith(prefix) for prefix in prefixes):extracted.writestr(name,original.read(name))
   artifact=target
  else:artifact=source
 subprocess.run([args.maven,'-N','org.apache.maven.plugins:maven-install-plugin:3.1.3:install-file','-Dfile='+str(artifact),'-DgroupId='+row['group'],'-DartifactId='+row['artifact'],'-Dversion='+row['version'],'-Dpackaging=jar','-DgeneratePom=true'],check=True)
print('Verified historical APIs installed; this does not certify full server readiness.')
