package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class rw extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ y6 k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ rw(y6 y6Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = y6Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((rw) i(ijVar, hkVar)).k(x31Var);
            case 1:
                return ((rw) i(ijVar, hkVar)).k(x31Var);
            case 2:
                return ((rw) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((rw) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new rw(this.k, ijVar, 0);
            case 1:
                return new rw(this.k, ijVar, 1);
            case 2:
                return new rw(this.k, ijVar, 2);
            default:
                return new rw(this.k, ijVar, 3);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
        switch (i) {
            case 0:
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                ch0 ch0Var = new ch0(0L);
                this.j = 1;
                if (y6.c(this.k, ch0Var, null, null, null, this, 14) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 1:
                int i3 = this.j;
                if (i3 != 0) {
                    if (i3 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                Float f = new Float(1.0f);
                this.j = 1;
                if (y6.c(this.k, f, null, null, null, this, 14) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 2:
                int i4 = this.j;
                if (i4 != 0) {
                    if (i4 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                Float f2 = new Float(0.0f);
                this.j = 1;
                if (y6.c(this.k, f2, null, null, null, this, 14) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                int i5 = this.j;
                if (i5 != 0) {
                    if (i5 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                Float f3 = new Float(0.0f);
                ay0 ay0Var = new ay0(1.0f, 300.0f, new Float(0.5f));
                this.j = 1;
                if (y6.c(this.k, f3, ay0Var, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }
}
