package defpackage;

import android.content.Intent;
import android.os.Bundle;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ag {
    public final LinkedHashMap a = new LinkedHashMap();
    public final LinkedHashMap b = new LinkedHashMap();
    public final LinkedHashMap c = new LinkedHashMap();
    public final ArrayList d = new ArrayList();
    public final transient LinkedHashMap e = new LinkedHashMap();
    public final LinkedHashMap f = new LinkedHashMap();
    public final Bundle g = new Bundle();
    public final /* synthetic */ MainActivity h;

    public ag(MainActivity mainActivity) {
        this.h = mainActivity;
    }

    public final boolean a(int i, int i2, Intent intent) {
        g2 g2Var;
        String str = (String) this.a.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        d2 d2Var = (d2) this.e.get(str);
        if (d2Var != null) {
            g2Var = d2Var.a;
        } else {
            g2Var = null;
        }
        if (g2Var != null) {
            ArrayList arrayList = this.d;
            if (arrayList.contains(str)) {
                g2 g2Var2 = d2Var.a;
                ((gv) ((af0) g2Var2.a).getValue()).e(d2Var.b.i(intent, i2));
                arrayList.remove(str);
                return true;
            }
        }
        this.f.remove(str);
        this.g.putParcelable(str, new w1(intent, i2));
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0013, code lost:
    
        if (r11 >= 2) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void b(int r10, defpackage.dt0 r11, defpackage.c4 r12) {
        /*
            Method dump skipped, instructions count: 426
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ag.b(int, dt0, c4):void");
    }
}
