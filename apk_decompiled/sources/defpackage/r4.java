package defpackage;

import android.util.LongSparseArray;
import com.kyant.backdrop.catalog.MainActivity;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class r4 implements Runnable {
    public final /* synthetic */ int e;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;

    public /* synthetic */ r4(int i, Object obj, Object obj2) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.e;
        Object obj = this.g;
        Object obj2 = this.f;
        switch (i) {
            case 0:
                o20.m((t4) obj2, (LongSparseArray) obj);
                return;
            case 1:
                MainActivity mainActivity = (MainActivity) obj2;
                mainActivity.e.a(new tf((mh0) obj, mainActivity));
                return;
            default:
                f81 f81Var = (f81) obj2;
                l80 l80Var = (l80) obj;
                if (!f81Var.g) {
                    f81Var.h = l80Var;
                    l80Var.a(f81Var);
                    return;
                }
                return;
        }
    }
}
