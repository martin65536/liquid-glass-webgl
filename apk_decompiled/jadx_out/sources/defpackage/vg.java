package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vg {
    public final c9 a = new c9(this);
    public final b9 b;
    public vu c;

    public vg(e9 e9Var) {
        this.b = new b9(this, e9Var);
    }

    public final void a(boolean z) {
        wf0 wf0Var;
        boolean z2;
        wf0 wf0Var2;
        c9 c9Var = this.a;
        c9Var.b = z;
        ArrayList arrayList = (ArrayList) c9Var.a;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            jh0 jh0Var = (jh0) obj;
            if (jh0Var.e && z) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (jh0Var.b != z2) {
                jh0Var.b = z2;
                e3 e3Var = jh0Var.c;
                if (e3Var != null && (wf0Var2 = (wf0) e3Var.b) != null) {
                    wf0Var2.b();
                }
            }
        }
        b9 b9Var = this.b;
        if (b9Var.b != z) {
            b9Var.b = z;
            e3 e3Var2 = b9Var.c;
            if (e3Var2 != null && (wf0Var = (wf0) e3Var2.b) != null) {
                wf0Var.b();
            }
        }
    }
}
