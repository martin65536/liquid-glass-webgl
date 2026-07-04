package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ez extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public /* synthetic */ Object k;
    public final /* synthetic */ zp l;
    public final /* synthetic */ gl m;
    public final /* synthetic */ mm n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ez(zp zpVar, gl glVar, mm mmVar, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.l = zpVar;
        this.m = glVar;
        this.n = mmVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        cq0 cq0Var = (cq0) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                ((ez) i(ijVar, cq0Var)).k(x31Var);
                return ik.e;
            default:
                return ((ez) i(ijVar, cq0Var)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                ez ezVar = new ez(this.l, this.m, this.n, ijVar, 0);
                ezVar.k = obj;
                return ezVar;
            default:
                ez ezVar2 = new ez(this.l, this.m, this.n, ijVar, 1);
                ezVar2.k = obj;
                return ezVar2;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        gl glVar = this.m;
        zp zpVar = this.l;
        ik ikVar = ik.e;
        switch (i) {
            case 0:
                cq0 cq0Var = (cq0) this.k;
                int i2 = this.j;
                if (i2 != 0) {
                    if (i2 != 1) {
                        v7.o("call to 'resume' before 'invoke' with coroutine");
                        return null;
                    }
                    o30.x(obj);
                } else {
                    o30.x(obj);
                    String str = eq0.a(zpVar, cq0Var).a;
                    pb pbVar = new pb(7);
                    this.k = null;
                    this.j = 1;
                    obj = fz.a(str, str, glVar, pbVar, this);
                    if (obj == ikVar) {
                        return ikVar;
                    }
                }
                obj.getClass();
                v7.d();
                return null;
            default:
                cq0 cq0Var2 = (cq0) this.k;
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
                    String str2 = eq0.a(zpVar, cq0Var2).a;
                    l lVar = new l(4, this.n);
                    this.k = null;
                    this.j = 1;
                    obj = fz.a(str2, str2, glVar, lVar, this);
                    if (obj == ikVar) {
                        return ikVar;
                    }
                }
                obj.getClass();
                return ((az) obj).a;
        }
    }
}
