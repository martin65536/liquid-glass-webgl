"""Compare before/after press screenshots to verify the button scaled up."""
from PIL import Image
import sys

before = Image.open('/home/z/my-project/scripts/fresh-before.png').convert('RGB')
after = Image.open('/home/z/my-project/scripts/fresh-pressed-settled.png').convert('RGB')

print(f'before size: {before.size}')
print(f'after size:  {after.size}')

# The canvas is 420x900 CSS px, but the screenshot may have different dimensions.
# The button is at x=107.5..312.5, y=330..378 (48px tall, 205px wide).
# Let's crop a generous region around the topmost button and compare.

# First, find the canvas offset in the screenshot. The canvas is at left=20, top=0
# in the viewport. So button in screenshot coords:
# x: 20+107.5 = 127.5 to 20+312.5 = 332.5
# y: 330 to 378

# But screenshot may be scaled. Let's just crop a wide region and look for differences.
w, h = before.size
print(f'screenshot dimensions: {w}x{h}')

# Crop the button region (with some margin) from both
# Button is at y=330..378 in canvas coords. Canvas starts at y=0 in viewport.
# So in screenshot, button y is roughly 330*h/900 to 378*h/900
y1 = int(330 * h / 900) - 5
y2 = int(378 * h / 900) + 30  # include shadow below
x1 = int(107 * w / 420) - 30
x2 = int(313 * w / 420) + 30

before_crop = before.crop((x1, y1, x2, y2))
after_crop = after.crop((x1, y1, x2, y2))
print(f'crop region: ({x1},{y1}) to ({x2},{y2}), size: {before_crop.size}')

# Compute pixel-wise difference
import numpy as np
before_arr = np.array(before_crop).astype(int)
after_arr = np.array(after_crop).astype(int)
diff = np.abs(after_arr - before_arr)
print(f'max diff: {diff.max()}')
print(f'mean diff: {diff.mean():.2f}')
print(f'pixels with diff > 5: {(diff.sum(axis=2) > 5).sum()}')

# Find the bounding box of differing pixels
mask = diff.sum(axis=2) > 10
if mask.any():
    rows = np.any(mask, axis=1)
    cols = np.any(mask, axis=0)
    rmin, rmax = np.where(rows)[0][[0, -1]]
    cmin, cmax = np.where(cols)[0][[0, -1]]
    print(f'diff bbox: rows {rmin}-{rmax} ({rmax-rmin+1}px tall), cols {cmin}-{cmax} ({cmax-cmin+1}px wide)')

# Save the crops and a diff visualization
before_crop.save('/home/z/my-project/scripts/before-crop.png')
after_crop.save('/home/z/my-project/scripts/after-crop.png')
# Amplified diff
diff_img = Image.fromarray(np.clip(diff * 5, 0, 255).astype(np.uint8))
diff_img.save('/home/z/my-project/scripts/diff.png')
print('saved crops and diff to /home/z/my-project/scripts/')
