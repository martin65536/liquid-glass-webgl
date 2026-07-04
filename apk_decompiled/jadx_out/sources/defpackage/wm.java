package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class wm implements gv {
    public final /* synthetic */ int e = 0;
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    public /* synthetic */ wm(ym ymVar, a20 a20Var, oe0 oe0Var, int i) {
        this.g = ymVar;
        this.h = a20Var;
        this.i = oe0Var;
        this.f = i;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i;
        int i2 = this.e;
        x31 x31Var = x31.a;
        Object obj2 = this.i;
        int i3 = this.f;
        Object obj3 = this.h;
        Object obj4 = this.g;
        switch (i2) {
            case 0:
                a20 a20Var = (a20) obj3;
                oe0 oe0Var = (oe0) obj2;
                if (obj != ((ym) obj4)) {
                    if (obj instanceof ny0) {
                        int i4 = a20Var.a - i3;
                        int d = oe0Var.d(obj);
                        if (d >= 0) {
                            i = oe0Var.c[d];
                        } else {
                            i = Integer.MAX_VALUE;
                        }
                        oe0Var.g(Math.min(i4, i), obj);
                        return x31Var;
                    }
                    return x31Var;
                }
                v7.o("A derived state calculation cannot read itself");
                return null;
            default:
                em0[] em0VarArr = (em0[]) obj4;
                pr0 pr0Var = (pr0) obj3;
                int[] iArr = (int[]) obj2;
                dm0 dm0Var = (dm0) obj;
                int length = em0VarArr.length;
                int i5 = 0;
                int i6 = 0;
                while (i5 < length) {
                    em0 em0Var = em0VarArr[i5];
                    em0Var.getClass();
                    em0Var.A();
                    dm0.z(dm0Var, em0Var, iArr[i6], Math.round((1.0f + pr0Var.b.a) * ((i3 - em0Var.f) / 2.0f)));
                    i5++;
                    i6++;
                }
                return x31Var;
        }
    }

    public /* synthetic */ wm(em0[] em0VarArr, pr0 pr0Var, int i, int[] iArr) {
        this.g = em0VarArr;
        this.h = pr0Var;
        this.f = i;
        this.i = iArr;
    }
}
