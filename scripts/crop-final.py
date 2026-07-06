"""Crop button 1 from rest, press-peak, and release-undershoot frames."""
from PIL import Image
import os

frames = [
    ('1-rest', '/home/z/my-project/scripts/final-rest.png'),
    ('2-press-peak', '/home/z/my-project/scripts/final-press-peak.png'),
    ('3-release-undershoot', '/home/z/my-project/scripts/final-release-undershoot.png'),
]

# Button 1 in screenshot coords (460x900 screenshot, canvas at x=20):
# x=127.5..332.5, y=330..378. Add 15px margin for scale-up + shadow.
x1, y1, x2, y2 = 110, 320, 350, 395

crops = []
for label, path in frames:
    if not os.path.exists(path):
        continue
    img = Image.open(path).convert('RGB')
    crop = img.crop((x1, y1, x2, y2))
    crop.save(f'/home/z/my-project/scripts/final-crop-{label}.png')
    crops.append((label, crop))

# Create horizontal composite (side by side)
total_w = sum(c.size[0] for _, c in crops) + 10 * (len(crops) - 1)
max_h = max(c.size[1] for _, c in crops)
composite = Image.new('RGB', (total_w, max_h + 30), (255, 255, 255))
x = 0
for label, crop in crops:
    composite.paste(crop, (x, 30))
    x += crop.size[0] + 10

composite.save('/home/z/my-project/scripts/final-composite.png')
print(f'saved {len(crops)} crops and composite ({composite.size})')
