[Setup]
AppName=ResolveRPC
AppVersion=1.0
PrivilegesRequired=admin
DefaultDirName={commonpf64}\ResolveRPC
DefaultGroupName=ResolveRPC
OutputBaseFilename=ResolveRPC-Setup
Compression=lzma
SolidCompression=yes

[Files]
Source: "target\ResolveRPC.exe"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{group}\ResolveRPC"; Filename: "{app}\ResolveRPC.exe"; WorkingDir: "{app}"; IconFilename: "{app}\ResolveRPC.exe"
Name: "{commonstartup}\ResolveRPC"; Filename: "{app}\ResolveRPC.exe"; WorkingDir: "{app}"; IconFilename: "{app}\ResolveRPC.exe"

[Run]
Filename: "{app}\ResolveRPC.exe"; WorkingDir: "{app}"; Description: "Launch ResolveRPC now"; Flags: nowait postinstall skipifsilent

[Code]

procedure KillResolveRPC();
var
  ResultCode: Integer;
begin
  Exec('taskkill', '/F /IM ResolveRPC.exe', '', SW_HIDE, ewWaitUntilTerminated, ResultCode);
end;

function InitializeSetup(): Boolean;
begin
  KillResolveRPC();
  Result := True;
end;