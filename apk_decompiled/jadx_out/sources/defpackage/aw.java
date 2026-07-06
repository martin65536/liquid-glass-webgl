package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class aw {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ aw(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final void a() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj;
                bwVar.A--;
                return;
            default:
                nx0 nx0Var = (nx0) obj;
                nx0Var.k--;
                return;
        }
    }

    public final void b() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                ((bw) obj).A++;
                return;
            default:
                ((nx0) obj).k++;
                return;
        }
    }
}
