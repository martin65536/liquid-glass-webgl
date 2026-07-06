package defpackage;

import android.view.View;
import android.view.Window;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class uf implements h80 {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;

    public /* synthetic */ uf(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        Window window;
        View peekDecorView;
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                MainActivity mainActivity = (MainActivity) obj;
                if (z70Var == z70.ON_STOP && (window = mainActivity.getWindow()) != null && (peekDecorView = window.peekDecorView()) != null) {
                    peekDecorView.cancelPendingInputEvents();
                    return;
                }
                return;
            case 1:
                MainActivity mainActivity2 = (MainActivity) obj;
                if (z70Var == z70.ON_DESTROY) {
                    mainActivity2.f.b = null;
                    if (!mainActivity2.isChangingConfigurations()) {
                        LinkedHashMap linkedHashMap = mainActivity2.c().e;
                        Iterator it = linkedHashMap.values().iterator();
                        while (it.hasNext()) {
                            ((s51) it.next()).a();
                        }
                        linkedHashMap.clear();
                    }
                    yf yfVar = mainActivity2.j;
                    MainActivity mainActivity3 = yfVar.h;
                    mainActivity3.getWindow().getDecorView().removeCallbacks(yfVar);
                    mainActivity3.getWindow().getDecorView().getViewTreeObserver().removeOnDrawListener(yfVar);
                    return;
                }
                return;
            default:
                os0 os0Var = (os0) obj;
                if (z70Var == z70.ON_START) {
                    os0Var.h = true;
                    return;
                } else {
                    if (z70Var == z70.ON_STOP) {
                        os0Var.h = false;
                        return;
                    }
                    return;
                }
        }
    }
}
