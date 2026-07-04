package defpackage;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g4 extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ h4 g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g4(h4 h4Var, int i) {
        super(1);
        this.f = i;
        this.g = h4Var;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        h4 h4Var = this.g;
        switch (i) {
            case 0:
                View view = h4Var.h;
                return Boolean.valueOf(view.getParent().requestSendAccessibilityEvent(view, (AccessibilityEvent) obj));
            default:
                kt0 kt0Var = (kt0) obj;
                if (kt0Var.f.contains(kt0Var)) {
                    pj0 snapshotObserver = h4Var.h.getSnapshotObserver();
                    snapshotObserver.a.b(kt0Var, h4Var.Q, new u3(1, kt0Var, h4Var));
                }
                return x31.a;
        }
    }
}
