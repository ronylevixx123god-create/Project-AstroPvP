#!/usr/bin/env python3
"""Verify original LuckPerms runtime libraries against its embedded checksums."""
from pathlib import Path
import json,hashlib,zipfile
root=Path(__file__).resolve().parent.parent
for row in json.loads((root/'tools/luckperms-runtime-manifest.json').read_text()):
    path=Path(row['path'])
    if not path.exists():raise SystemExit('Required retained LuckPerms library is missing: '+str(path))
    if hashlib.sha256(path.read_bytes()).hexdigest()!=row['sha256']:raise SystemExit('LuckPerms embedded checksum mismatch: '+str(path))
    with zipfile.ZipFile(path) as archive:
        if archive.testzip():raise SystemExit('Corrupt LuckPerms library: '+str(path))
print('PASS: 19 exact LuckPerms runtime libraries match original embedded checksums.')
