package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class e81 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ f81 k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ e81(f81 f81Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = f81Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((e81) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((e81) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        f81 f81Var = this.k;
        switch (i) {
            case 0:
                return new e81(f81Var, ijVar, 0);
            default:
                return new e81(f81Var, ijVar, 1);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        f81 f81Var = this.k;
        ik ikVar = ik.e;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 == 1) {
                        o30.x(obj);
                    } else {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                } else {
                    o30.x(obj);
                    b4 b4Var = f81Var.e;
                    this.j = 1;
                    Object d = b4Var.D.d(this);
                    if (d != ikVar) {
                        d = x31Var;
                    }
                    if (d == ikVar) {
                        return ikVar;
                    }
                }
                return x31Var;
            default:
                int i3 = this.j;
                if (i3 != 0) {
                    if (i3 == 1) {
                        o30.x(obj);
                    } else {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                } else {
                    o30.x(obj);
                    b4 b4Var2 = f81Var.e;
                    this.j = 1;
                    Object g = b4Var2.E.g(this);
                    if (g != ikVar) {
                        g = x31Var;
                    }
                    if (g == ikVar) {
                        return ikVar;
                    }
                }
                return x31Var;
        }
    }
}
