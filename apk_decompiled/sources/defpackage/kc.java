package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kc implements wg0 {
    public final /* synthetic */ int a;
    public final Object b;

    public /* synthetic */ kc(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final String toString() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return "CancelHandler.UserSupplied[" + ((gv) obj).getClass().getSimpleName() + '@' + dl.v(this) + ']';
            default:
                return "DisposeOnCancel[" + ((un) obj) + ']';
        }
    }
}
