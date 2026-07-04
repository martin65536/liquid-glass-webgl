package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class el implements z6 {
    public final e3 a;
    public final c4 b;
    public final Object c;
    public final i7 d;
    public final i7 e;
    public final i7 f;
    public final Object g;
    public final long h;

    public el(fl flVar, c4 c4Var, Object obj, i7 i7Var) {
        e3 e3Var = new e3(flVar.a);
        this.a = e3Var;
        this.b = c4Var;
        this.c = obj;
        i7 i7Var2 = (i7) ((gv) c4Var.f).e(obj);
        this.d = i7Var2;
        this.e = dl.p(i7Var);
        gv gvVar = (gv) c4Var.g;
        if (((i7) e3Var.d) == null) {
            e3Var.d = i7Var2.c();
        }
        i7 i7Var3 = (i7) e3Var.d;
        if (i7Var3 != null) {
            int b = i7Var3.b();
            int i = 0;
            while (true) {
                i7 i7Var4 = (i7) e3Var.d;
                if (i < b) {
                    if (i7Var4 != null) {
                        j2 j2Var = (j2) e3Var.a;
                        float a = i7Var2.a(i);
                        float a2 = i7Var.a(i);
                        double b2 = ((hs) j2Var.f).b(a2);
                        double d = is.a;
                        int i2 = i;
                        i7Var4.e((Math.signum(a2) * ((float) (Math.exp((d / (d - 1.0d)) * b2) * r13.a * r13.b))) + a, i2);
                        i = i2 + 1;
                    } else {
                        o20.G("targetVector");
                        throw null;
                    }
                } else {
                    if (i7Var4 != null) {
                        this.g = gvVar.e(i7Var4);
                        e3 e3Var2 = this.a;
                        i7 i7Var5 = this.d;
                        if (((i7) e3Var2.c) == null) {
                            e3Var2.c = i7Var5.c();
                        }
                        i7 i7Var6 = (i7) e3Var2.c;
                        if (i7Var6 != null) {
                            int b3 = i7Var6.b();
                            long j = 0;
                            for (int i3 = 0; i3 < b3; i3++) {
                                j2 j2Var2 = (j2) e3Var2.a;
                                i7Var5.getClass();
                                j = Math.max(j, ((long) (Math.exp(((hs) j2Var2.f).b(i7Var.a(i3)) / (is.a - 1.0d)) * 1000.0d)) * 1000000);
                            }
                            this.h = j;
                            i7 p = dl.p(this.a.n(j, this.d, i7Var));
                            this.f = p;
                            int b4 = p.b();
                            for (int i4 = 0; i4 < b4; i4++) {
                                i7 i7Var7 = this.f;
                                float a3 = i7Var7.a(i4);
                                this.a.getClass();
                                this.a.getClass();
                                i7Var7.e(n30.i(a3, -0.0f, 0.0f), i4);
                            }
                            return;
                        }
                        o20.G("velocityVector");
                        throw null;
                    }
                    o20.G("targetVector");
                    throw null;
                }
            }
        } else {
            o20.G("targetVector");
            throw null;
        }
    }

    @Override // defpackage.z6
    public final boolean a() {
        return false;
    }

    @Override // defpackage.z6
    public final Object b(long j) {
        float f;
        if (!d3.a(this, j)) {
            gv gvVar = (gv) this.b.g;
            e3 e3Var = this.a;
            i7 i7Var = (i7) e3Var.b;
            i7 i7Var2 = this.d;
            if (i7Var == null) {
                e3Var.b = i7Var2.c();
            }
            i7 i7Var3 = (i7) e3Var.b;
            if (i7Var3 != null) {
                int b = i7Var3.b();
                int i = 0;
                while (true) {
                    i7 i7Var4 = (i7) e3Var.b;
                    if (i < b) {
                        if (i7Var4 != null) {
                            j2 j2Var = (j2) e3Var.a;
                            float a = i7Var2.a(i);
                            long j2 = j / 1000000;
                            gs a2 = ((hs) j2Var.f).a(this.e.a(i));
                            long j3 = a2.c;
                            if (j3 > 0) {
                                f = ((float) j2) / ((float) j3);
                            } else {
                                f = 1.0f;
                            }
                            i7Var4.e((Math.signum(a2.a) * a2.b * j5.a(f).a) + a, i);
                            i++;
                        } else {
                            o20.G("valueVector");
                            throw null;
                        }
                    } else {
                        if (i7Var4 != null) {
                            return gvVar.e(i7Var4);
                        }
                        o20.G("valueVector");
                        throw null;
                    }
                }
            } else {
                o20.G("valueVector");
                throw null;
            }
        } else {
            return this.g;
        }
    }

    @Override // defpackage.z6
    public final long c() {
        return this.h;
    }

    @Override // defpackage.z6
    public final c4 d() {
        return this.b;
    }

    @Override // defpackage.z6
    public final Object e() {
        return this.g;
    }

    @Override // defpackage.z6
    public final i7 f(long j) {
        if (!d3.a(this, j)) {
            return this.a.n(j, this.d, this.e);
        }
        return this.f;
    }

    @Override // defpackage.z6
    public final /* synthetic */ boolean g(long j) {
        return d3.a(this, j);
    }
}
