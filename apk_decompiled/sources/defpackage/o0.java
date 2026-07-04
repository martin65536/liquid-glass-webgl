package defpackage;

import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class o0 extends ClickableSpan {
    public final int e;
    public final k1 f;
    public final int g;

    public o0(int i, k1 k1Var, int i2) {
        this.e = i;
        this.f = k1Var;
        this.g = i2;
    }

    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("ACCESSIBILITY_CLICKABLE_SPAN_ID", this.e);
        this.f.a.performAction(this.g, bundle);
    }
}
