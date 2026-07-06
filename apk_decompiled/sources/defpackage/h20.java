package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h20 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ k20 k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h20(k20 k20Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = k20Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
            case 1:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
            case 2:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
            case 3:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
            case 4:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((h20) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        k20 k20Var = this.k;
        switch (i) {
            case 0:
                return new h20(k20Var, ijVar, 0);
            case 1:
                return new h20(k20Var, ijVar, 1);
            case 2:
                return new h20(k20Var, ijVar, 2);
            case 3:
                return new h20(k20Var, ijVar, 3);
            case 4:
                return new h20(k20Var, ijVar, 4);
            default:
                return new h20(k20Var, ijVar, 5);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        k20 k20Var = this.k;
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
                y6 y6Var = k20Var.e;
                Float f = new Float(1.0f);
                ay0 ay0Var = k20Var.c;
                this.j = 1;
                if (y6.c(y6Var, f, ay0Var, null, null, this, 12) == ikVar) {
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
                y6 y6Var2 = k20Var.f;
                ch0 ch0Var = new ch0(k20Var.g);
                this.j = 1;
                if (y6Var2.e(ch0Var, this) == ikVar) {
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
                y6 y6Var3 = k20Var.e;
                Float f2 = new Float(0.0f);
                ay0 ay0Var2 = k20Var.c;
                this.j = 1;
                if (y6.c(y6Var3, f2, ay0Var2, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 3:
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
                y6 y6Var4 = k20Var.f;
                ch0 ch0Var2 = new ch0(k20Var.g);
                ay0 ay0Var3 = k20Var.d;
                this.j = 1;
                if (y6.c(y6Var4, ch0Var2, ay0Var3, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            case 4:
                int i6 = this.j;
                if (i6 != 0) {
                    if (i6 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                y6 y6Var5 = k20Var.e;
                Float f3 = new Float(0.0f);
                ay0 ay0Var4 = k20Var.c;
                this.j = 1;
                if (y6.c(y6Var5, f3, ay0Var4, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                int i7 = this.j;
                if (i7 != 0) {
                    if (i7 == 1) {
                        o30.x(obj);
                        return x31Var;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                y6 y6Var6 = k20Var.f;
                ch0 ch0Var3 = new ch0(k20Var.g);
                ay0 ay0Var5 = k20Var.d;
                this.j = 1;
                if (y6.c(y6Var6, ch0Var3, ay0Var5, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }
}
