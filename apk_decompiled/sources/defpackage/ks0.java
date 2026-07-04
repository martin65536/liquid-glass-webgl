package defpackage;

import android.os.Bundle;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.Arrays;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ks0 implements ns0 {
    public final c4 a;
    public boolean b;
    public Bundle c;
    public final a01 d;

    public ks0(c4 c4Var, MainActivity mainActivity) {
        c4Var.getClass();
        this.a = c4Var;
        this.d = new a01(new sf(mainActivity, 5));
    }

    @Override // defpackage.ns0
    public final Bundle a() {
        Bundle l = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
        Bundle bundle = this.c;
        if (bundle != null) {
            l.putAll(bundle);
        }
        for (Map.Entry entry : ((ls0) this.d.getValue()).b.entrySet()) {
            String str = (String) entry.getKey();
            Bundle a = ((vf) ((is0) entry.getValue()).a.e).a();
            if (!a.isEmpty()) {
                str.getClass();
                l.putBundle(str, a);
            }
        }
        this.b = false;
        return l;
    }

    public final void b() {
        if (!this.b) {
            Bundle m = this.a.m("androidx.lifecycle.internal.SavedStateHandlesProvider");
            Bundle l = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
            Bundle bundle = this.c;
            if (bundle != null) {
                l.putAll(bundle);
            }
            if (m != null) {
                l.putAll(m);
            }
            this.c = l;
            this.b = true;
        }
    }
}
