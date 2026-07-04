package defpackage;

import android.util.SparseArray;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vc0 {
    public final SparseArray a;
    public n31 b;

    public vc0(int i) {
        this.a = new SparseArray(i);
    }

    public final void a(n31 n31Var, int i, int i2) {
        vc0 vc0Var;
        int a = n31Var.a(i);
        SparseArray sparseArray = this.a;
        if (sparseArray == null) {
            vc0Var = null;
        } else {
            vc0Var = (vc0) sparseArray.get(a);
        }
        if (vc0Var == null) {
            vc0Var = new vc0(1);
            sparseArray.put(n31Var.a(i), vc0Var);
        }
        if (i2 > i) {
            vc0Var.a(n31Var, i + 1, i2);
        } else {
            vc0Var.b = n31Var;
        }
    }
}
