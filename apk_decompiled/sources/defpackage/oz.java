package defpackage;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.kyant.backdrop.catalog.MainActivity;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oz implements h80 {
    public static final a01 f = new a01(new c2(15));
    public final MainActivity e;

    public oz(MainActivity mainActivity) {
        this.e = mainActivity;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        if (z70Var == z70.ON_DESTROY) {
            Object systemService = this.e.getSystemService("input_method");
            systemService.getClass();
            InputMethodManager inputMethodManager = (InputMethodManager) systemService;
            lz lzVar = (lz) f.getValue();
            Object b = lzVar.b(inputMethodManager);
            if (b != null) {
                synchronized (b) {
                    View c = lzVar.c(inputMethodManager);
                    if (c == null) {
                        return;
                    }
                    if (c.isAttachedToWindow()) {
                        return;
                    }
                    boolean a = lzVar.a(inputMethodManager);
                    if (a) {
                        inputMethodManager.isActive();
                    }
                }
            }
        }
    }
}
