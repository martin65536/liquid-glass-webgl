package defpackage;

import android.view.DragEvent;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a5 implements View.OnDragListener, go {
    public final ho a;
    public final h8 b;
    public final z4 c;

    /* JADX WARN: Type inference failed for: r0v0, types: [ho, bd0] */
    public a5() {
        ?? bd0Var = new bd0();
        bd0Var.u = 0L;
        this.a = bd0Var;
        this.b = new h8();
        this.c = new z4(this);
    }

    /* JADX WARN: Type inference failed for: r6v2, types: [ap0, java.lang.Object] */
    @Override // android.view.View.OnDragListener
    public final boolean onDrag(View view, DragEvent dragEvent) {
        j2 j2Var = new j2(6, dragEvent);
        int action = dragEvent.getAction();
        v21 v21Var = v21.e;
        h8 h8Var = this.b;
        ho hoVar = this.a;
        switch (action) {
            case 1:
                ?? obj = new Object();
                q2 q2Var = new q2(j2Var, hoVar, obj);
                if (q2Var.e(hoVar) == v21Var) {
                    d20.M(hoVar, q2Var);
                }
                boolean z = obj.e;
                h8Var.getClass();
                c8 c8Var = new c8(h8Var);
                while (c8Var.hasNext()) {
                    ((ho) c8Var.next()).H0();
                }
                return z;
            case 2:
                hoVar.G0(j2Var);
                return false;
            case 3:
                return hoVar.D0();
            case 4:
                q2 q2Var2 = new q2(12, j2Var);
                if (q2Var2.e(hoVar) == v21Var) {
                    d20.M(hoVar, q2Var2);
                }
                h8Var.clear();
                return false;
            case 5:
                hoVar.E0();
                return false;
            case 6:
                hoVar.F0();
                return false;
            default:
                return false;
        }
    }
}
