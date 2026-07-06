package defpackage;

import android.os.Bundle;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hs0 implements es0, ps0 {
    public final /* synthetic */ fs0 e;
    public l80 f;
    public c4 g;

    public hs0(fs0 fs0Var) {
        Bundle bundle;
        this.e = fs0Var;
        Object e = fs0Var.e("androidx.savedstate.SavedStateRegistry");
        if (e instanceof Bundle) {
            bundle = (Bundle) e;
        } else {
            bundle = null;
        }
        if (bundle != null) {
            g(bundle);
        }
        fs0Var.a("androidx.savedstate.SavedStateRegistry", new f6(5, this));
    }

    @Override // defpackage.es0
    public final r7 a(String str, vu vuVar) {
        return this.e.a(str, vuVar);
    }

    @Override // defpackage.ps0
    public final c4 b() {
        return (c4) g(null).g;
    }

    @Override // defpackage.es0
    public final boolean c(Object obj) {
        return this.e.c(obj);
    }

    @Override // defpackage.es0
    public final Map d() {
        return this.e.d();
    }

    @Override // defpackage.es0
    public final Object e(String str) {
        return this.e.e(str);
    }

    @Override // defpackage.j80
    public final l80 f() {
        l80 l80Var = this.f;
        if (l80Var == null) {
            l80 l80Var2 = new l80(this, false);
            this.f = l80Var2;
            return l80Var2;
        }
        return l80Var;
    }

    public final c4 g(Bundle bundle) {
        c4 c4Var = this.g;
        if (c4Var == null) {
            c4 c4Var2 = new c4(new os0(this, new f6(6, this)), 20);
            this.g = c4Var2;
            c4Var2.s(bundle);
            return c4Var2;
        }
        return c4Var;
    }
}
