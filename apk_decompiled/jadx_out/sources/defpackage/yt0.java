package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yt0 extends sz0 implements kv {
    public final /* synthetic */ int i;
    public int j;
    public final /* synthetic */ zt0 k;
    public /* synthetic */ long l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ yt0(zt0 zt0Var, long j, ij ijVar, int i) {
        super(2, ijVar);
        this.i = i;
        this.k = zt0Var;
        this.l = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        int i = this.i;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                return ((yt0) i((ij) obj2, (hk) obj)).k(x31Var);
            case 1:
                return ((yt0) i((ij) obj2, (hk) obj)).k(x31Var);
            case 2:
                return ((yt0) i((ij) obj2, (hk) obj)).k(x31Var);
            default:
                long j = ((ch0) obj).a;
                yt0 yt0Var = new yt0(this.k, (ij) obj2);
                yt0Var.l = j;
                return yt0Var.k(x31Var);
        }
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        switch (this.i) {
            case 0:
                return new yt0(this.k, this.l, ijVar, 0);
            case 1:
                return new yt0(this.k, this.l, ijVar, 1);
            case 2:
                return new yt0(this.k, this.l, ijVar, 2);
            default:
                yt0 yt0Var = new yt0(this.k, ijVar);
                yt0Var.l = ((ch0) obj).a;
                return yt0Var;
        }
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.i;
        x31 x31Var = x31.a;
        zt0 zt0Var = this.k;
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
                hu0 hu0Var = zt0Var.R;
                xt0 xt0Var = new xt0(this.l, null);
                this.j = 1;
                if (hu0Var.f(gf0.f, xt0Var, this) == ikVar) {
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
                hu0 hu0Var2 = zt0Var.R;
                long j = this.l;
                this.j = 1;
                if (hu0Var2.b(j, false, this) == ikVar) {
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
                hu0 hu0Var3 = zt0Var.R;
                long j2 = this.l;
                this.j = 1;
                if (hu0Var3.b(j2, true, this) == ikVar) {
                    return ikVar;
                }
                return x31Var;
            default:
                int i5 = this.j;
                if (i5 != 0) {
                    if (i5 == 1) {
                        o30.x(obj);
                        return obj;
                    }
                    v7.o("call to 'resume' before 'invoke' with coroutine");
                    return null;
                }
                o30.x(obj);
                long j3 = this.l;
                hu0 hu0Var4 = zt0Var.R;
                this.j = 1;
                Object h = n20.h(hu0Var4, j3, this);
                if (h == ikVar) {
                    return ikVar;
                }
                return h;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public yt0(zt0 zt0Var, ij ijVar) {
        super(2, ijVar);
        this.i = 3;
        this.k = zt0Var;
    }
}
