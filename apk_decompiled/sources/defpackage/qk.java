package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qk extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ al k;
    public final /* synthetic */ float l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ qk(al alVar, float f, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = alVar;
        this.l = f;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((qk) i(ijVar, hkVar)).k(x31Var);
            case 1:
                return ((qk) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((qk) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        float f = this.l;
        al alVar = this.k;
        switch (i) {
            case 0:
                return new qk(alVar, f, ijVar, 0);
            case 1:
                return new qk(alVar, f, ijVar, 1);
            default:
                return new qk(alVar, f, ijVar, 2);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        float f = this.l;
        ik ikVar = ik.e;
        al alVar = this.k;
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
                y6 y6Var = alVar.l;
                Float f2 = new Float(f);
                ay0 ay0Var = alVar.g;
                this.j = 1;
                if (y6.c(y6Var, f2, ay0Var, null, null, this, 12) == ikVar) {
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
                y6 y6Var2 = alVar.l;
                Float f3 = new Float(f);
                ay0 ay0Var2 = alVar.g;
                uk ukVar = new uk(alVar, 2);
                this.j = 1;
                if (y6.c(y6Var2, f3, ay0Var2, null, ukVar, this, 4) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
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
                y6 y6Var3 = alVar.m;
                Float f4 = new Float(f);
                ay0 ay0Var3 = alVar.h;
                this.j = 1;
                if (y6.c(y6Var3, f4, ay0Var3, null, null, this, 12) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }
}
