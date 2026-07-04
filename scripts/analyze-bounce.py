"""Analyze button width in bounce frames - fixed detection."""
from PIL import Image
import numpy as np
import os

def analyze_button(img_path):
    """Detect button bounds by looking at the button row."""
    img = Image.open(img_path).convert('RGB')
    arr = np.array(img)
    h, w = arr.shape[:2]
    
    # Button 1 is at y=330..378 in canvas coords. Canvas starts at y=0 in screenshot.
    # So button 1 row is y=330..378 in screenshot.
    # Center y = 354.
    y_center = 354
    
    # Sample a horizontal strip across the button row and average to reduce noise
    strip = arr[y_center - 5 : y_center + 5, :, :].astype(int).mean(axis=0)
    
    # The wallpaper background is dark (teal/blue). The glass button is LIGHTER
    # because it has vibrancy/saturation boost and surface tint.
    # Let's compute brightness (R+G+B) and find the button region.
    brightness = strip.sum(axis=1)
    
    # Find the brightest region (the button)
    # Background brightness ~ 100-200, button brightness ~ 200-400
    threshold = brightness.mean() + 30
    mask = brightness > threshold
    
    if not mask.any():
        return 0, 0, 0, 0
    
    cols = np.where(mask)[0]
    cmin, cmax = cols[0], cols[-1]
    
    return cmax - cmin + 1, cmin, cmax, int(brightness[cols].mean())

frames = [
    ('rest', '/home/z/my-project/scripts/bounce-0-rest.png'),
    ('pressed', '/home/z/my-project/scripts/bounce-1-pressed.png'),
    ('50ms', '/home/z/my-project/scripts/bounce-50ms-after-release.png'),
    ('100ms', '/home/z/my-project/scripts/bounce-100ms-after-release.png'),
    ('150ms', '/home/z/my-project/scripts/bounce-150ms-after-release.png'),
    ('200ms', '/home/z/my-project/scripts/bounce-200ms-after-release.png'),
    ('250ms', '/home/z/my-project/scripts/bounce-250ms-after-release.png'),
]

print(f'{"frame":<12} {"width":<8} {"cmin":<6} {"cmax":<6} {"brightness":<12}')
print('-' * 50)
prev_w = None
for label, path in frames:
    if not os.path.exists(path):
        print(f'{label:<12} missing')
        continue
    w, cmin, cmax, brightness = analyze_button(path)
    delta = f'{w - prev_w:+d}px' if prev_w is not None else ''
    print(f'{label:<12} {w:<8} {cmin:<6} {cmax:<6} {brightness:<12} {delta}')
    prev_w = w
