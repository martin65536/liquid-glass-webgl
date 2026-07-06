package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pf implements ij {
    public static final pf f = new pf(0);
    public static final pf g = new pf(1);
    public final /* synthetic */ int e;

    public /* synthetic */ pf(int i) {
        this.e = i;
    }

    @Override // defpackage.ij
    public final yj r() {
        switch (this.e) {
            case 0:
                throw new IllegalStateException("This continuation is already complete");
            default:
                return cr.e;
        }
    }

    public String toString() {
        switch (this.e) {
            case 0:
                return "This continuation is already complete";
            default:
                return super.toString();
        }
    }

    @Override // defpackage.ij
    public final void u(Object obj) {
        switch (this.e) {
            case 0:
                throw new IllegalStateException("This continuation is already complete");
            default:
                return;
        }
    }

    private final void a(Object obj) {
    }
}
