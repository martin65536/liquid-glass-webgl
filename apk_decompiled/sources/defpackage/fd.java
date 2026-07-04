package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class fd extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public /* synthetic */ Object k;
    public final /* synthetic */ gd l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ fd(gd gdVar, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.l = gdVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((fd) i((ij) obj2, (un0) obj)).k(x31Var);
            default:
                return ((fd) i((ij) obj2, (ps) obj)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        gd gdVar = this.l;
        switch (i) {
            case 0:
                fd fdVar = new fd(gdVar, ijVar, 0);
                fdVar.k = obj;
                return fdVar;
            default:
                fd fdVar2 = new fd(gdVar, ijVar, 1);
                fdVar2.k = obj;
                return fdVar2;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        gd gdVar = this.l;
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
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
                    un0 un0Var = (un0) this.k;
                    this.j = 1;
                    Object e = gdVar.e(new kv0(un0Var), this);
                    if (e != ikVar) {
                        e = x31Var;
                    }
                    if (e == ikVar) {
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
                    ps psVar = (ps) this.k;
                    this.j = 1;
                    if (gdVar.e(psVar, this) == ikVar) {
                        return ikVar;
                    }
                }
                return x31Var;
        }
    }
}
