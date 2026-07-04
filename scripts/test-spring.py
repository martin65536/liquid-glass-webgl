"""Test the closed-form underdamped spring to verify bounce behavior."""
import math

# Spring constants (matching the TS code)
K = 300
DAMPING_RATIO = 0.5
OMEGA_N = math.sqrt(K)  # 17.3205
OMEGA_D = OMEGA_N * math.sqrt(1 - DAMPING_RATIO ** 2)  # 15.0

def spring_step(current, velocity, target, dt):
    """Closed-form underdamped spring step."""
    x0 = current - target
    v0 = velocity
    
    decay = math.exp(-DAMPING_RATIO * OMEGA_N * dt)
    cos_wd = math.cos(OMEGA_D * dt)
    sin_wd = math.sin(OMEGA_D * dt)
    
    offset = (
        x0 * decay * cos_wd +
        ((v0 + DAMPING_RATIO * OMEGA_N * x0) / OMEGA_D) * decay * sin_wd
    )
    
    b0 = (v0 + DAMPING_RATIO * OMEGA_N * x0) / OMEGA_D
    new_vel = (
        -DAMPING_RATIO * OMEGA_N * offset +
        decay * (-x0 * OMEGA_D * sin_wd + b0 * OMEGA_D * cos_wd)
    )
    
    return target + offset, new_vel

# Simulate a press: p goes from 0 to 1
print("=== PRESS (0 -> 1) ===")
p, v = 0.0, 0.0
target = 1.0
dt = 1/60  # 60fps
peak_p = 0
for frame in range(60):  # 1 second
    p, v = spring_step(p, v, target, dt)
    if abs(p) > abs(peak_p):
        peak_p = p
    if frame in [0, 5, 10, 12, 15, 20, 30, 45, 59]:
        print(f"  frame {frame:3d} ({frame*dt:.3f}s): p={p:.4f} v={v:.4f}")

print(f"\n  Peak p during press: {peak_p:.4f} (overshoot {(peak_p-1)*100:.1f}%)")
print(f"  Press scale at peak: {1 + (4/48) * peak_p:.4f}")
print(f"  Press scale at settle: {1 + (4/48) * 1.0:.4f}")

# Simulate release: p goes from 1 to 0
print("\n=== RELEASE (1 -> 0) ===")
p, v = 1.0, 0.0
target = 0.0
min_p = 1.0
for frame in range(60):
    p, v = spring_step(p, v, target, dt)
    if p < min_p:
        min_p = p
    if frame in [0, 5, 10, 12, 15, 20, 30, 45, 59]:
        print(f"  frame {frame:3d} ({frame*dt:.3f}s): p={p:.4f} v={v:.4f}")

print(f"\n  Min p during release: {min_p:.4f} (undershoot {min_p*100:.1f}%)")
print(f"  Release scale at undershoot: {1 + (4/48) * min_p:.4f}")
print(f"  Release scale at settle: {1 + (4/48) * 0.0:.4f}")
print(f"\n  Bounce amplitude (peak-to-peak): {(peak_p - 1 - min_p)*100:.1f}% of press delta")
