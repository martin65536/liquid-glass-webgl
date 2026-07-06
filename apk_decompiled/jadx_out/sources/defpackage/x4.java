package defpackage;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import org.jetbrains.compose.resources.AndroidContextProvider;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class x4 {
    public static final void a(bw bwVar, int i) {
        boolean z;
        bwVar.W(1587247798);
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i & 1, z)) {
            if (((Boolean) bwVar.j(n10.a)).booleanValue()) {
                bwVar.V(-1890697985);
                Context context = AndroidContextProvider.e;
                AndroidContextProvider.e = (Context) bwVar.j(p4.b);
                bwVar.p(false);
            } else {
                bwVar.V(-1890623988);
                bwVar.p(false);
            }
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new w4(i);
        }
    }

    public static final Context b() {
        return InstrumentationRegistry.getInstrumentation().getContext();
    }
}
