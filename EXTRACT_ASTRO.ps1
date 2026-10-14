$ErrorActionPreference = 'Stop'
try {
    $manifest = Get-Content -LiteralPath (Join-Path $PSScriptRoot 'download-manifest.json') -Raw | ConvertFrom-Json
    $archive = Join-Path $PSScriptRoot $manifest.archive
    $destination = Join-Path $PSScriptRoot 'AstroPvP-Windows-Test'
    if (Test-Path -LiteralPath $destination) { throw 'The AstroPvP-Windows-Test folder already exists. Use a fresh folder to avoid overwriting your changes.' }
    foreach ($part in $manifest.parts) {
        $path = Join-Path $PSScriptRoot $part.name
        if (!(Test-Path -LiteralPath $path)) { throw ('Missing file: ' + $part.name) }
        if ((Get-Item -LiteralPath $path).Length -ne $part.bytes) { throw ('Wrong file size: ' + $part.name) }
        if ((Get-FileHash -LiteralPath $path -Algorithm SHA256).Hash.ToLowerInvariant() -ne $part.sha256) { throw ('Checksum mismatch: ' + $part.name) }
    }
    if (!(Test-Path -LiteralPath $archive)) {
        $output = [IO.File]::Open($archive, [IO.FileMode]::CreateNew, [IO.FileAccess]::Write)
        try {
            foreach ($part in $manifest.parts) {
                $inputFile = [IO.File]::OpenRead((Join-Path $PSScriptRoot $part.name))
                try { $inputFile.CopyTo($output) } finally { $inputFile.Dispose() }
            }
        } finally { $output.Dispose() }
    }
    if ((Get-FileHash -LiteralPath $archive -Algorithm SHA256).Hash.ToLowerInvariant() -ne $manifest.archive_sha256) { throw 'The assembled ZIP checksum does not match. No files were extracted.' }
    Expand-Archive -LiteralPath $archive -DestinationPath $PSScriptRoot
    Write-Host 'AstroPvP files extracted successfully.'
    Write-Host 'The server is not running. Java 8 and a separately configured test database are still required.'
    Write-Host 'Do not use the existing Docker setup/start scripts for a native Windows installation.'
    Start-Process explorer.exe -ArgumentList ('"' + $destination + '"')
} catch {
    Write-Host $_.Exception.Message -ForegroundColor Red
    exit 1
}
