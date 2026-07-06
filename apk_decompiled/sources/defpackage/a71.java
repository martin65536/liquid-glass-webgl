package defpackage;

import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.util.Objects;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class a71 extends z61 {
    public a71(k71 k71Var, WindowInsets windowInsets) {
        super(k71Var, windowInsets);
    }

    @Override // defpackage.g71
    public k71 a() {
        WindowInsets consumeDisplayCutout;
        consumeDisplayCutout = this.c.consumeDisplayCutout();
        return k71.b(consumeDisplayCutout, null);
    }

    @Override // defpackage.y61, defpackage.g71
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a71)) {
            return false;
        }
        a71 a71Var = (a71) obj;
        if (Objects.equals(this.c, a71Var.c) && Objects.equals(this.g, a71Var.g) && y61.K(this.h, a71Var.h)) {
            return true;
        }
        return false;
    }

    @Override // defpackage.g71
    public on g() {
        DisplayCutout displayCutout;
        displayCutout = this.c.getDisplayCutout();
        if (displayCutout == null) {
            return null;
        }
        return new on(displayCutout);
    }

    @Override // defpackage.g71
    public int hashCode() {
        return this.c.hashCode();
    }
}
