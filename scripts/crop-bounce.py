"""Crop button 1 region from bounce frames for visual inspection."""
from PIL import Image
import os

# Button 1: x=107.5..312.5, y=330..378 in canvas coords (420x900 canvas).
# Screenshot is 460x900. Canvas is at x=20..440 in screenshot.
# So button 1 in screenshot: x=127.5..332.5, y=330..378.
# Add margin for the press scale-up (~10px each side) and shadow.

frames = [
    ('rest', '/home/z/my-project/scripts/bounce-0-rest.png'),
    ('pressed', '/home/z/my-project/scripts/bounce-1-pressed.png'),
    ('50ms', '/home/z/my-project/scripts/bounce-50ms-after-release.png'),
    ('100ms', '/home/z/my-project/scripts/bounce-100ms-after-release.png'),
    ('150ms', '/home/z/my-project/scripts/bounce-150ms-after-release.png'),
    ('200ms', '/home/z/my-project/scripts/bounce-200ms-after-release.png'),
    ('250ms', '/home/z/my-project/scripts/bounce-250ms-after-release.png'),
]

# Crop region: x=110..350, y=320..390 (40px margin around button)
x1, y1, x2, y2 = 110, 320, 350, 390

# Create a composite image stacking all crops vertically
crops = []
for label, path in frames:
    if not os.path.exists(path):
        continue
    img = Image.open(path).convert('RGB')
    crop = img.crop((x1, y1, x2, y2))
    crops.append((label, crop))

# Save individual crops
for label, crop in crops:
    crop.save(f'/home/z/my-project/scripts/bounce-crop-{label}.png')

# Create vertical composite with labels
total_h = sum(c.size[1] for _, c in crops) + 20 * len(crops)
max_w = max(c.size[0] for _, c in crops)
composite = Image.new('RGB', (max_w, total_h), (255, 255, 255))
y = 0
for label, crop in crops:
    composite.paste(crop, (0, y))
    y += crop.size[1] + 20

composite.save('/home/z/my-project/scripts/bounce-composite.png')
print(f'saved {len(crops)} crops and composite ({composite.size})')
