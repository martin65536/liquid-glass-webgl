package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x7 implements w7, y7 {
    public final float e;
    public final boolean f;
    public final v7 g;
    public final float h;

    public x7(float f, boolean z, v7 v7Var) {
        this.e = f;
        this.f = z;
        this.g = v7Var;
        this.h = f;
    }

    @Override // defpackage.w7
    public final float a() {
        return this.h;
    }

    @Override // defpackage.w7
    public final void d(mm mmVar, int i, int[] iArr, m40 m40Var, int[] iArr2) {
        boolean z;
        int i2;
        int round;
        if (iArr.length != 0) {
            int S = mmVar.S(this.e);
            if (this.f && m40Var == m40.f) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                int length = iArr.length;
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i3 < length) {
                    int max = Math.max(0, i - iArr[i3]);
                    iArr2[i5] = max;
                    i4 = Math.min(S, max);
                    i = iArr2[i5] - i4;
                    i3++;
                    i5++;
                }
                i2 = i + i4;
            } else {
                int length2 = iArr.length;
                int i6 = 0;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (i6 < length2) {
                    int i10 = iArr[i6];
                    int min = Math.min(i7, i - i10);
                    iArr2[i9] = min;
                    int min2 = Math.min(S, (i - min) - i10);
                    int i11 = iArr2[i9] + i10 + min2;
                    i6++;
                    i8 = min2;
                    i7 = i11;
                    i9++;
                }
                i2 = i - (i7 - i8);
            }
            if (i2 > 0) {
                int i12 = this.g.e;
                float f = -1.0f;
                m40 m40Var2 = m40.e;
                float f2 = 0.0f;
                switch (i12) {
                    case 0:
                        float f3 = i2 / 2.0f;
                        if (m40Var != m40Var2) {
                            f = 1.0f;
                        }
                        round = Math.round((1.0f + f) * f3);
                        break;
                    case 1:
                        float f4 = (i2 + 0) / 2.0f;
                        if (m40Var != m40Var2) {
                            f2 = 0.0f * (-1.0f);
                        }
                        round = Math.round((1.0f + f2) * f4);
                        break;
                    default:
                        round = Math.round((1.0f + 0.0f) * ((i2 + 0) / 2.0f));
                        break;
                }
                if (z) {
                    round -= i2;
                }
                if (round != 0) {
                    int length3 = iArr2.length;
                    for (int i13 = 0; i13 < length3; i13++) {
                        iArr2[i13] = iArr2[i13] + round;
                    }
                }
            }
        }
    }

    @Override // defpackage.y7
    public final void e(int i, rc0 rc0Var, int[] iArr, int[] iArr2) {
        d(rc0Var, i, iArr, m40.e, iArr2);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof x7) {
                x7 x7Var = (x7) obj;
                if (!eo.a(this.e, x7Var.e) || this.f != x7Var.f || !this.g.equals(x7Var.g)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        int floatToIntBits = Float.floatToIntBits(this.e) * 31;
        if (this.f) {
            i = 1231;
        } else {
            i = 1237;
        }
        return this.g.hashCode() + ((floatToIntBits + i) * 31);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (this.f) {
            str = "";
        } else {
            str = "Absolute";
        }
        sb.append(str);
        sb.append("Arrangement#spacedAligned(");
        sb.append((Object) eo.b(this.e));
        sb.append(", ");
        sb.append(this.g);
        sb.append(')');
        return sb.toString();
    }
}
