package defpackage;

import android.os.Bundle;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class os0 {
    public final ps0 a;
    public final f6 b;
    public boolean e;
    public Bundle f;
    public boolean g;
    public final ey0 c = new ey0(5);
    public final LinkedHashMap d = new LinkedHashMap();
    public boolean h = true;

    public os0(ps0 ps0Var, f6 f6Var) {
        this.a = ps0Var;
        this.b = f6Var;
    }

    public final void a() {
        ps0 ps0Var = this.a;
        if (ps0Var.f().c == a80.f) {
            if (!this.e) {
                this.b.a();
                ps0Var.f().a(new uf(2, this));
                this.e = true;
                return;
            }
            v7.o("SavedStateRegistry was already attached.");
            return;
        }
        v7.o("Restarter must be created only during owner's initialization stage");
    }
}
