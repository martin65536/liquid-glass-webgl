package defpackage;

import android.graphics.Insets;
import android.view.WindowInsets;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class b71 extends a71 {
    public g10 s;
    public g10 t;
    public g10 u;

    public b71(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
        this.s = null;
        this.t = null;
        this.u = null;
    }

    @Override // defpackage.g71
    public g10 j() {
        Insets mandatorySystemGestureInsets;
        if (this.t == null) {
            mandatorySystemGestureInsets = this.c.getMandatorySystemGestureInsets();
            this.t = g10.c(mandatorySystemGestureInsets);
        }
        return this.t;
    }

    @Override // defpackage.g71
    public g10 l() {
        Insets systemGestureInsets;
        if (this.s == null) {
            systemGestureInsets = this.c.getSystemGestureInsets();
            this.s = g10.c(systemGestureInsets);
        }
        return this.s;
    }

    @Override // defpackage.g71
    public g10 n() {
        Insets tappableElementInsets;
        if (this.u == null) {
            tappableElementInsets = this.c.getTappableElementInsets();
            this.u = g10.c(tappableElementInsets);
        }
        return this.u;
    }

    @Override // defpackage.z61, defpackage.g71
    public void w(g10 g10Var) {
    }
}
