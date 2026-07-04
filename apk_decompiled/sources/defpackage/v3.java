package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class v3 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ ep0 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ v3(int i, ep0 ep0Var) {
        super(1);
        this.f = i;
        this.g = ep0Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        boolean z;
        int i = this.f;
        ep0 ep0Var = this.g;
        switch (i) {
            case 0:
                ep0Var.e = (pt) obj;
                return Boolean.TRUE;
            default:
                im imVar = (w21) obj;
                if (((bd0) imVar).e.r) {
                    ep0Var.e = imVar;
                    z = false;
                } else {
                    z = true;
                }
                return Boolean.valueOf(z);
        }
    }
}
