package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class id extends sz0 implements kv {
    public final /* synthetic */ int i = 1;
    public int j;
    public /* synthetic */ Object k;
    public final /* synthetic */ ld l;
    public final /* synthetic */ ps m;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public id(ld ldVar, ps psVar, Object obj, ij ijVar) {
        super(2, ijVar);
        this.l = ldVar;
        this.m = psVar;
        this.k = obj;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        hk hkVar = (hk) obj;
        ij ijVar = (ij) obj2;
        switch (i) {
            case 0:
                return ((id) i(ijVar, hkVar)).k(x31Var);
            default:
                return ((id) i(ijVar, hkVar)).k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        int i = this.i;
        ps psVar = this.m;
        ld ldVar = this.l;
        switch (i) {
            case 0:
                return new id(ldVar, psVar, this.k, ijVar);
            default:
                id idVar = new id(ldVar, psVar, ijVar);
                idVar.k = obj;
                return idVar;
        }
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Object, java.io.Serializable] */
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
                lv lvVar = this.l.i;
                Object obj2 = this.k;
                this.j = 1;
                if (lvVar.c(this.m, obj2, this) == ikVar) {
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
                hk hkVar = (hk) this.k;
                ?? obj3 = new Object();
                ld ldVar = this.l;
                os osVar = ldVar.h;
                kd kdVar = new kd(obj3, hkVar, ldVar, this.m, 0);
                this.j = 1;
                if (osVar.b(kdVar, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public id(ld ldVar, ps psVar, ij ijVar) {
        super(2, ijVar);
        this.l = ldVar;
        this.m = psVar;
    }
}
