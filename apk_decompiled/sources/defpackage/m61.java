package defpackage;

import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimation$Callback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class m61 extends WindowInsetsAnimation$Callback {
    public final g61 a;
    public List b;
    public ArrayList c;
    public final HashMap d;

    public m61(g61 g61Var) {
        super(g61Var.f);
        this.d = new HashMap();
        this.a = g61Var;
    }

    public final p61 a(WindowInsetsAnimation windowInsetsAnimation) {
        HashMap hashMap = this.d;
        p61 p61Var = (p61) hashMap.get(windowInsetsAnimation);
        if (p61Var == null) {
            p61 p61Var2 = new p61(0, null, 0L);
            p61Var2.a = new n61(windowInsetsAnimation);
            hashMap.put(windowInsetsAnimation, p61Var2);
            return p61Var2;
        }
        return p61Var;
    }

    public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        this.a.b(a(windowInsetsAnimation));
        this.d.remove(windowInsetsAnimation);
    }

    public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        a(windowInsetsAnimation);
        this.a.c();
    }

    public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
        float fraction;
        ArrayList arrayList = this.c;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList(list.size());
            this.c = arrayList2;
            this.b = Collections.unmodifiableList(arrayList2);
        } else {
            arrayList.clear();
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            WindowInsetsAnimation j = b1.j(list.get(size));
            p61 a = a(j);
            fraction = j.getFraction();
            a.a.e(fraction);
            this.c.add(a);
        }
        return this.a.d(k71.b(windowInsets, null), this.b).a();
    }

    public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        c4 e = this.a.e(a(windowInsetsAnimation), new c4(bounds));
        e.getClass();
        b1.l();
        return b1.h(((g10) e.f).d(), ((g10) e.g).d());
    }
}
