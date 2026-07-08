#!/usr/bin/env python3
"""Daemonize Next.js dev server so it survives parent shell exit."""
import os
import sys
import subprocess
import time
from pathlib import Path

PROJECT = Path("/home/z/my-project")
LOG_OUT = PROJECT / "dev.out.log"
LOG_DEV = PROJECT / "dev.log"

# Clear old logs
for p in [LOG_OUT, LOG_DEV]:
    if p.exists():
        p.unlink()

# Double-fork daemonization via start_new_session=True
# Child will be reparented to init (PID 1), fully detached
proc = subprocess.Popen(
    ["bun", "run", "dev"],
    cwd=str(PROJECT),
    stdin=subprocess.DEVNULL,
    stdout=open(LOG_OUT, "wb"),
    stderr=subprocess.STDOUT,
    start_new_session=True,  # setsid - new session, no controlling terminal
    close_fds=True,
)

# Write PID file for later management
(PROJECT / "dev.pid").write_text(str(proc.pid))

# Wait for server to come up
for _ in range(30):
    if proc.poll() is not None:
        print(f"ERROR: process exited with code {proc.returncode}")
        print("--- dev.out.log ---")
        print(LOG_OUT.read_text())
        sys.exit(1)
    try:
        import urllib.request
        urllib.request.urlopen("http://localhost:3000", timeout=1)
        print(f"OK: dev server is up on http://localhost:3000 (PID {proc.pid})")
        break
    except Exception:
        time.sleep(1)
else:
    print(f"WARN: server not responding after 30s (PID {proc.pid})")
    print("--- dev.out.log ---")
    print(LOG_OUT.read_text()[-2000:])
