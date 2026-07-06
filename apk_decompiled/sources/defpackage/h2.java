package defpackage;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class h2 implements sn {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ h2(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // defpackage.sn
    public final void a() {
        Integer num;
        int i = this.a;
        Object obj = null;
        Object obj2 = this.b;
        switch (i) {
            case 0:
                e2 e2Var = ((b2) obj2).a;
                if (e2Var != null) {
                    ag agVar = e2Var.w;
                    String str = e2Var.x;
                    Bundle bundle = agVar.g;
                    LinkedHashMap linkedHashMap = agVar.f;
                    str.getClass();
                    if (!agVar.d.contains(str) && (num = (Integer) agVar.b.remove(str)) != null) {
                        agVar.a.remove(num);
                    }
                    agVar.e.remove(str);
                    if (linkedHashMap.containsKey(str)) {
                        Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + linkedHashMap.get(str));
                        linkedHashMap.remove(str);
                    }
                    if (bundle.containsKey(str)) {
                        if (Build.VERSION.SDK_INT >= 34) {
                            obj = g1.c(bundle, str);
                        } else {
                            Parcelable parcelable = bundle.getParcelable(str);
                            if (w1.class.isInstance(parcelable)) {
                                obj = parcelable;
                            }
                        }
                        Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + ((w1) obj));
                        bundle.remove(str);
                    }
                    if (agVar.c.get(str) != null) {
                        v7.d();
                        return;
                    }
                    return;
                }
                v7.o("Launcher has not been initialized");
                return;
            case 1:
                ((vn) obj2).f.a();
                return;
            case 2:
                ((d60) obj2).d = null;
                return;
            case 3:
                q60 q60Var = (q60) obj2;
                c9 c9Var = q60Var.c;
                if (c9Var != null) {
                    c9Var.b = false;
                }
                q60Var.c = null;
                return;
            case 4:
                ((l60) obj2).f = true;
                return;
            default:
                s31 s31Var = (s31) obj2;
                s31Var.c.unregisterListener(s31Var.e);
                return;
        }
    }
}
