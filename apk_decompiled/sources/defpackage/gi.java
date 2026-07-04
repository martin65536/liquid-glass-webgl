package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gi extends do0 {
    public final /* synthetic */ int b = 0;
    public final Object c;

    public gi(gv gvVar) {
        super(new c2(5));
        this.c = new hi(gvVar);
    }

    @Override // defpackage.do0
    public final eo0 a(Object obj) {
        boolean z;
        boolean z2;
        switch (this.b) {
            case 0:
                if (obj == null) {
                    z = true;
                } else {
                    z = false;
                }
                return new eo0(this, obj, z, null, true);
            default:
                if (obj == null) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                return new eo0(this, obj, z2, (ix0) this.c, true);
        }
    }

    @Override // defpackage.do0
    public i41 b() {
        switch (this.b) {
            case 0:
                return (hi) this.c;
            default:
                return super.b();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public gi(vu vuVar) {
        super(vuVar);
        dt0 dt0Var = dt0.g;
        this.c = dt0Var;
    }
}
