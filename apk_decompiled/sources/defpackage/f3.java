package defpackage;

import android.graphics.Rect;
import android.view.autofill.AutofillManager;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f3 extends z30 implements mv {
    public final /* synthetic */ g3 f;
    public final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public f3(g3 g3Var, int i) {
        super(4);
        this.f = g3Var;
        this.g = i;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        int intValue3 = ((Number) obj3).intValue();
        int intValue4 = ((Number) obj4).intValue();
        g3 g3Var = this.f;
        j2 j2Var = g3Var.e;
        ((AutofillManager) j2Var.f).notifyViewEntered(g3Var.g, this.g, new Rect(intValue, intValue2, intValue3, intValue4));
        return x31.a;
    }
}
