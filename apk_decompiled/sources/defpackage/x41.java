package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x41 {
    public final boolean a;
    public final w41 b;
    public final int c;
    public final bl[] d;
    public int e;
    public final float[] f;
    public final float[] g;
    public final float[] h;

    public x41(boolean z, w41 w41Var) {
        int i;
        this.a = z;
        this.b = w41Var;
        if (z && w41Var.equals(w41.e)) {
            v7.o("Lsq2 not (yet) supported for differential axes");
            throw null;
        }
        int ordinal = w41Var.ordinal();
        if (ordinal != 0) {
            if (ordinal == 1) {
                i = 2;
            } else {
                v7.k();
                throw null;
            }
        } else {
            i = 3;
        }
        this.c = i;
        this.d = new bl[20];
        this.f = new float[20];
        this.g = new float[20];
        this.h = new float[3];
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Object, bl] */
    public final void a(long j, float f) {
        int i = (this.e + 1) % 20;
        this.e = i;
        bl[] blVarArr = this.d;
        bl blVar = blVarArr[i];
        if (blVar == 0) {
            ?? obj = new Object();
            obj.a = j;
            obj.b = f;
            blVarArr[i] = obj;
            return;
        }
        blVar.a = j;
        blVar.b = f;
    }

    public final float b(float f) {
        w41 w41Var;
        float[] fArr;
        float[] fArr2;
        float f2;
        boolean z;
        int i;
        float f3;
        float f4;
        float f5;
        int i2;
        float f6 = 0.0f;
        if (f <= 0.0f) {
            q00.b("maximumVelocity should be a positive value. You specified=" + f);
        }
        int i3 = this.e;
        bl[] blVarArr = this.d;
        bl blVar = blVarArr[i3];
        if (blVar == null) {
            f3 = 0.0f;
            f2 = 0.0f;
        } else {
            int i4 = 0;
            bl blVar2 = blVar;
            while (true) {
                bl blVar3 = blVarArr[i3];
                boolean z2 = this.a;
                w41Var = this.b;
                fArr = this.f;
                fArr2 = this.g;
                if (blVar3 == null) {
                    f2 = f6;
                    z = z2;
                    i = 1;
                    break;
                }
                long j = blVar.a;
                f2 = f6;
                int i5 = i3;
                long j2 = blVar3.a;
                float f7 = (float) (j - j2);
                z = z2;
                i = 1;
                float abs = (float) Math.abs(j2 - blVar2.a);
                if (w41Var != w41.e && !z) {
                    blVar2 = blVar;
                } else {
                    blVar2 = blVar3;
                }
                if (f7 > 100.0f || abs > 40.0f) {
                    break;
                }
                fArr[i4] = blVar3.b;
                fArr2[i4] = -f7;
                if (i5 == 0) {
                    i2 = 20;
                } else {
                    i2 = i5;
                }
                i3 = i2 - 1;
                i4++;
                if (i4 >= 20) {
                    break;
                }
                f6 = f2;
            }
            if (i4 >= this.c) {
                int ordinal = w41Var.ordinal();
                if (ordinal != 0) {
                    if (ordinal == i) {
                        int i6 = i4 - i;
                        float f8 = fArr2[i6];
                        int i7 = i6;
                        float f9 = f2;
                        while (i7 > 0) {
                            int i8 = i7 - 1;
                            float f10 = fArr2[i8];
                            if (f8 != f10) {
                                if (z) {
                                    f5 = -fArr[i8];
                                } else {
                                    f5 = fArr[i7] - fArr[i8];
                                }
                                float f11 = f5 / (f8 - f10);
                                f9 += Math.abs(f11) * (f11 - (Math.signum(f9) * ((float) Math.sqrt(Math.abs(f9) * 2.0f))));
                                if (i7 == i6) {
                                    f9 *= 0.5f;
                                }
                            }
                            i7--;
                            f8 = f10;
                        }
                        f4 = Math.signum(f9) * ((float) Math.sqrt(Math.abs(f9) * 2.0f));
                    } else {
                        v7.k();
                        return f2;
                    }
                } else {
                    try {
                        float[] fArr3 = this.h;
                        d20.D(fArr2, fArr, i4, fArr3);
                        f4 = fArr3[1];
                    } catch (IllegalArgumentException unused) {
                        f4 = f2;
                    }
                }
                f3 = f4 * 1000.0f;
            } else {
                f3 = f2;
            }
        }
        if (f3 == f2 || Float.isNaN(f3)) {
            return f2;
        }
        if (f3 > f2) {
            if (f3 > f) {
                f3 = f;
            }
        } else {
            float f12 = -f;
            if (f3 < f12) {
                return f12;
            }
        }
        return f3;
    }

    public /* synthetic */ x41() {
        this(false, w41.e);
    }

    public x41(int i) {
        this(true, w41.f);
    }
}
