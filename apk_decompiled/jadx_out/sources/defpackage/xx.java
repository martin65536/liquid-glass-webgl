package defpackage;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xx extends ak implements hm {
    public final Handler g;
    public final String h;
    public final boolean i;
    public final xx j;

    public xx(Handler handler, String str, boolean z) {
        xx xxVar;
        this.g = handler;
        this.h = str;
        this.i = z;
        if (z) {
            xxVar = this;
        } else {
            xxVar = new xx(handler, str, true);
        }
        this.j = xxVar;
    }

    @Override // defpackage.hm
    public final un d(long j, final f21 f21Var, yj yjVar) {
        if (j > 4611686018427387903L) {
            j = 4611686018427387903L;
        }
        if (this.g.postDelayed(f21Var, j)) {
            return new un() { // from class: vx
                @Override // defpackage.un
                public final void a() {
                    xx.this.g.removeCallbacks(f21Var);
                }
            };
        }
        q(yjVar, f21Var);
        return sg0.e;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof xx) {
            xx xxVar = (xx) obj;
            if (xxVar.g == this.g && xxVar.i == this.i) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // defpackage.hm
    public final void f(long j, pc pcVar) {
        wx wxVar = new wx(pcVar, this);
        if (j > 4611686018427387903L) {
            j = 4611686018427387903L;
        }
        if (this.g.postDelayed(wxVar, j)) {
            pcVar.w(new c(9, this, wxVar));
        } else {
            q(pcVar.i, wxVar);
        }
    }

    @Override // defpackage.ak
    public final void g(yj yjVar, Runnable runnable) {
        if (!this.g.post(runnable)) {
            q(yjVar, runnable);
        }
    }

    public final int hashCode() {
        int i;
        int identityHashCode = System.identityHashCode(this.g);
        if (this.i) {
            i = 1231;
        } else {
            i = 1237;
        }
        return i ^ identityHashCode;
    }

    @Override // defpackage.ak
    public final boolean k(yj yjVar) {
        if (this.i && o20.e(Looper.myLooper(), this.g.getLooper())) {
            return false;
        }
        return true;
    }

    public final void q(yj yjVar, Runnable runnable) {
        CancellationException cancellationException = new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed");
        d30 d30Var = (d30) yjVar.j(x1.L);
        if (d30Var != null) {
            d30Var.a(cancellationException);
        }
        bm bmVar = mn.a;
        vl.g.g(yjVar, runnable);
    }

    @Override // defpackage.ak
    public final String toString() {
        xx xxVar;
        String str;
        bm bmVar = mn.a;
        xx xxVar2 = yb0.a;
        if (this == xxVar2) {
            str = "Dispatchers.Main";
        } else {
            try {
                xxVar = xxVar2.j;
            } catch (UnsupportedOperationException unused) {
                xxVar = null;
            }
            if (this == xxVar) {
                str = "Dispatchers.Main.immediate";
            } else {
                str = null;
            }
        }
        if (str == null) {
            String str2 = this.h;
            if (str2 == null) {
                str2 = this.g.toString();
            }
            if (this.i) {
                return str2 + ".immediate";
            }
            return str2;
        }
        return str;
    }

    public xx(Handler handler) {
        this(handler, null, false);
    }
}
