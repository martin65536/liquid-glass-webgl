"""Capture frames during press release to verify Q-bounce (undershoot)."""
import subprocess
import time
import os

# First, reload page to reset state
subprocess.run(['agent-browser', 'open', 'http://localhost:3000'], capture_output=True, text=True)
time.sleep(2)

# Take resting screenshot
subprocess.run(['agent-browser', 'screenshot', '/home/z/my-project/scripts/bounce-0-rest.png'], capture_output=True, text=True)
print('captured rest state')

# Press and hold (dispatch pointerdown)
press_js = '''JSON.stringify((() => {
  const c = document.querySelector("canvas");
  if (!c) return "no canvas";
  const r = c.getBoundingClientRect();
  c.dispatchEvent(new PointerEvent("pointerdown", {
    bubbles: true, cancelable: true, composed: true,
    clientX: r.left + 210, clientY: r.top + 354,
    pointerId: 1, pointerType: "mouse",
    isPrimary: true, button: 0, buttons: 1, width: 1, height: 1, pressure: 0.5,
  }));
  return {ok: true};
})())'''
subprocess.run(['agent-browser', 'eval', press_js], capture_output=True, text=True)

# Wait for press to settle (spring peak ~0.21s, settle ~0.5s)
time.sleep(0.4)
subprocess.run(['agent-browser', 'screenshot', '/home/z/my-project/scripts/bounce-1-pressed.png'], capture_output=True, text=True)
print('captured pressed state')

# Now release (dispatch pointerup)
release_js = '''JSON.stringify((() => {
  const c = document.querySelector("canvas");
  if (!c) return "no canvas";
  const r = c.getBoundingClientRect();
  c.dispatchEvent(new PointerEvent("pointerup", {
    bubbles: true, cancelable: true, composed: true,
    clientX: r.left + 210, clientY: r.top + 354,
    pointerId: 1, pointerType: "mouse",
    isPrimary: true, button: 0, buttons: 0, width: 1, height: 1, pressure: 0,
  }));
  return {ok: true};
})())'''
subprocess.run(['agent-browser', 'eval', release_js], capture_output=True, text=True)
print('released')

# Capture frames at various delays to catch the bounce
# Spring spec: peak undershoot at ~0.21s after release
for delay_ms in [50, 100, 150, 200, 250, 300, 400, 600, 800]:
    time.sleep(delay_ms / 1000 - (0 if delay_ms == 50 else (delay_ms - 50) / 1000 - 0.05))
    path = f'/home/z/my-project/scripts/bounce-{delay_ms}ms-after-release.png'
    subprocess.run(['agent-browser', 'screenshot', path], capture_output=True, text=True)
    print(f'captured at {delay_ms}ms after release')

print('done')
