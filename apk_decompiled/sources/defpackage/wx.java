package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wx implements Runnable {
    public final /* synthetic */ int e = 0;
    public Runnable f;
    public final /* synthetic */ ak g;

    public wx(pc pcVar, xx xxVar) {
        this.f = pcVar;
        this.g = xxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.e;
        ak akVar = this.g;
        switch (i) {
            case 0:
                ((pc) this.f).H((xx) akVar, x31.a);
                return;
            default:
                r80 r80Var = (r80) akVar;
                ak akVar2 = r80Var.h;
                int i2 = 0;
                while (true) {
                    try {
                        this.f.run();
                    } catch (Throwable th) {
                        o4.K(cr.e, th);
                    }
                    Runnable q = r80Var.q();
                    if (q != null) {
                        this.f = q;
                        i2++;
                        if (i2 >= 16 && akVar2.k(r80Var)) {
                            akVar2.g(r80Var, this);
                            return;
                        }
                    } else {
                        return;
                    }
                }
                break;
        }
    }

    public wx(r80 r80Var, Runnable runnable) {
        this.g = r80Var;
        this.f = runnable;
    }
}
