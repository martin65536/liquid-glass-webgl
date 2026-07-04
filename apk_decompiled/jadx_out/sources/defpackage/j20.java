package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j20 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ k20 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ j20(k20 k20Var, int i) {
        super(1);
        this.f = i;
        this.g = k20Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float f;
        float f2;
        int i = this.f;
        ij ijVar = null;
        int i2 = 3;
        x31 x31Var = x31.a;
        k20 k20Var = this.g;
        switch (i) {
            case 0:
                um0 um0Var = (um0) obj;
                um0Var.getClass();
                k20Var.g = um0Var.c;
                f31.G(k20Var.a, null, new i20(k20Var, ijVar, 0), 3);
                return x31Var;
            case 1:
                ((um0) obj).getClass();
                f31.G(k20Var.a, null, new i20(k20Var, ijVar, 1), 3);
                return x31Var;
            case 2:
                b50 b50Var = (b50) obj;
                b50Var.getClass();
                yc ycVar = b50Var.e;
                y6 y6Var = k20Var.e;
                h6 h6Var = k20Var.h;
                float floatValue = ((Number) y6Var.d()).floatValue();
                if (floatValue > 0.0f) {
                    if (h6Var != null) {
                        long j = se.c;
                        d3.q(b50Var, se.b(j, 0.08f * floatValue), 0L, 0.0f, 12, 62);
                        kv kvVar = k20Var.b;
                        r7 r7Var = ycVar.f;
                        r7 r7Var2 = ycVar.f;
                        long j2 = ((ch0) kvVar.d(new mw0(r7Var.v()), k20Var.f.d())).a;
                        h6Var.a.setFloatUniform("size", Float.intBitsToFloat((int) (r7Var2.v() >> 32)), Float.intBitsToFloat((int) (r7Var2.v() & 4294967295L)));
                        h6Var.a.setColorUniform("color", f31.P(se.b(j, floatValue * 0.15f)));
                        h6Var.a.setFloatUniform("radius", mw0.b(r7Var2.v()) * 1.5f);
                        float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32));
                        float intBitsToFloat2 = Float.intBitsToFloat((int) (r7Var2.v() >> 32));
                        if (intBitsToFloat < 0.0f) {
                            intBitsToFloat = 0.0f;
                        }
                        if (intBitsToFloat <= intBitsToFloat2) {
                            intBitsToFloat2 = intBitsToFloat;
                        }
                        float intBitsToFloat3 = Float.intBitsToFloat((int) (j2 & 4294967295L));
                        float intBitsToFloat4 = Float.intBitsToFloat((int) (r7Var2.v() & 4294967295L));
                        if (intBitsToFloat3 < 0.0f) {
                            intBitsToFloat3 = 0.0f;
                        }
                        if (intBitsToFloat3 <= intBitsToFloat4) {
                            intBitsToFloat4 = intBitsToFloat3;
                        }
                        h6Var.a.setFloatUniform("position", intBitsToFloat2, intBitsToFloat4);
                        ub ubVar = new ub(h6Var.a);
                        long j3 = d3.j(b50Var.j());
                        if ((62 & 8) != 0) {
                            f = 1.0f;
                        } else {
                            f = 0.0f;
                        }
                        yr yrVar = yr.s;
                        if ((62 & 64) == 0) {
                            i2 = 12;
                        }
                        int i3 = i2;
                        yc ycVar2 = b50Var.e;
                        int i4 = (int) (0 >> 32);
                        int i5 = (int) (0 & 4294967295L);
                        ycVar2.e.c.m(Float.intBitsToFloat(i4), Float.intBitsToFloat(i5), Float.intBitsToFloat((int) (j3 >> 32)) + Float.intBitsToFloat(i4), Float.intBitsToFloat((int) (j3 & 4294967295L)) + Float.intBitsToFloat(i5), ycVar2.u(ubVar, yrVar, f, null, i3, 1));
                    } else {
                        d3.q(b50Var, se.b(se.c, floatValue * 0.25f), 0L, 0.0f, 12, 62);
                    }
                }
                b50Var.r();
                return x31Var;
            default:
                lx lxVar = (lx) obj;
                lxVar.getClass();
                float intBitsToFloat5 = Float.intBitsToFloat((int) (lxVar.j() >> 32));
                float intBitsToFloat6 = Float.intBitsToFloat((int) (lxVar.j() & 4294967295L));
                float z = d20.z(1.0f, (lxVar.G(4.0f) / Float.intBitsToFloat((int) (lxVar.j() & 4294967295L))) + 1.0f, ((Number) k20Var.e.d()).floatValue());
                float b = mw0.b(lxVar.j());
                long f3 = ch0.f(((ch0) k20Var.f.d()).a, k20Var.g);
                lxVar.n(((float) Math.tanh((Float.intBitsToFloat(r0) * 0.05f) / b)) * b);
                int i6 = (int) (f3 & 4294967295L);
                lxVar.g(b * ((float) Math.tanh((Float.intBitsToFloat(i6) * 0.05f) / b)));
                float G = lxVar.G(4.0f) / Float.intBitsToFloat((int) (4294967295L & lxVar.j()));
                double atan2 = (float) Math.atan2(Float.intBitsToFloat(i6), Float.intBitsToFloat(r0));
                float intBitsToFloat7 = Float.intBitsToFloat((int) (f3 >> 32)) * ((float) Math.cos(atan2));
                long j4 = lxVar.j();
                float abs = Math.abs(intBitsToFloat7 / Math.max(Float.intBitsToFloat((int) ((j4 >> 32) & 2147483647L)), Float.intBitsToFloat((int) (j4 & 2147483647L)))) * G;
                float f4 = intBitsToFloat5 / intBitsToFloat6;
                if (f4 > 1.0f) {
                    f4 = 1.0f;
                }
                lxVar.i((abs * f4) + z);
                float intBitsToFloat8 = Float.intBitsToFloat(i6) * ((float) Math.sin(atan2));
                long j5 = lxVar.j();
                float abs2 = Math.abs(intBitsToFloat8 / Math.max(Float.intBitsToFloat((int) ((j5 >> 32) & 2147483647L)), Float.intBitsToFloat((int) (j5 & 2147483647L)))) * G;
                float f5 = intBitsToFloat6 / intBitsToFloat5;
                if (f5 > 1.0f) {
                    f2 = 1.0f;
                } else {
                    f2 = f5;
                }
                lxVar.q((abs2 * f2) + z);
                return x31Var;
        }
    }
}
