package defpackage;

import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class ey0 implements v51 {
    public static ey0 f;
    public static ey0 g;
    public final /* synthetic */ int e;

    public /* synthetic */ ey0(int i) {
        this.e = i;
    }

    public static final u6 d(String str, int i) {
        WeakHashMap weakHashMap = l71.v;
        return new u6(str, i);
    }

    public static final int e(int i, long j) {
        int i2 = o4.j;
        return ((int) (j >> (i * 15))) & 32767;
    }

    public static final j41 f(String str, int i) {
        WeakHashMap weakHashMap = l71.v;
        return new j41(new m10(0, 0, 0, 0), str);
    }

    public static l71 g(View view) {
        l71 l71Var;
        WeakHashMap weakHashMap = l71.v;
        synchronized (weakHashMap) {
            try {
                Object obj = weakHashMap.get(view);
                if (obj == null) {
                    obj = new l71(view);
                    weakHashMap.put(view, obj);
                }
                l71Var = (l71) obj;
            } catch (Throwable th) {
                throw th;
            }
        }
        return l71Var;
    }

    @Override // defpackage.v51
    public s51 a(Class cls) {
        return d20.k(cls);
    }

    @Override // defpackage.v51
    public s51 b(Class cls, ee0 ee0Var) {
        return a(cls);
    }

    @Override // defpackage.v51
    public s51 c(wd wdVar, ee0 ee0Var) {
        Class cls = wdVar.a;
        cls.getClass();
        return b(cls, ee0Var);
    }

    public boolean h(CharSequence charSequence) {
        return false;
    }

    public String toString() {
        switch (this.e) {
            case 0:
                return "SharingStarted.Eagerly";
            case 1:
                return "SharingStarted.Lazily";
            case 2:
            default:
                return super.toString();
            case 3:
                return "ReusedSlotId";
        }
    }
}
