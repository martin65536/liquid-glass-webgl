package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class k01 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ lv k;
    public final /* synthetic */ mn0 l;
    public final /* synthetic */ um0 m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ k01(lv lvVar, mn0 mn0Var, um0 um0Var, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = lvVar;
        this.l = mn0Var;
        this.m = um0Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((k01) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((k01) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new k01(this.k, this.l, this.m, ijVar, 0);
            default:
                return new k01(this.k, this.l, this.m, ijVar, 1);
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        um0 um0Var = this.m;
        mn0 mn0Var = this.l;
        lv lvVar = this.k;
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
                ch0 ch0Var = new ch0(um0Var.c);
                this.j = 1;
                if (lvVar.c(mn0Var, ch0Var, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
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
                ch0 ch0Var2 = new ch0(um0Var.c);
                this.j = 1;
                if (lvVar.c(mn0Var, ch0Var2, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }
}
