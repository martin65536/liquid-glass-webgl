package defpackage;

import android.os.Bundle;
import com.kyant.backdrop.catalog.MainActivity;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vo0 implements h80 {
    public final /* synthetic */ int e;
    public final Object f;

    public /* synthetic */ vo0(int i, Object obj) {
        this.e = i;
        this.f = obj;
    }

    @Override // defpackage.h80
    public final void h(j80 j80Var, z70 z70Var) {
        int i = this.e;
        Object obj = this.f;
        switch (i) {
            case 0:
                ps0 ps0Var = (ps0) obj;
                if (z70Var == z70.ON_CREATE) {
                    j80Var.f().f(this);
                    Bundle m = ps0Var.b().m("androidx.savedstate.Restarter");
                    if (m != null) {
                        ArrayList<String> stringArrayList = m.getStringArrayList("classes_to_restore");
                        if (stringArrayList != null) {
                            int size = stringArrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                String str = stringArrayList.get(i2);
                                i2++;
                                String str2 = str;
                                try {
                                    Class<? extends U> asSubclass = Class.forName(str2, false, vo0.class.getClassLoader()).asSubclass(ms0.class);
                                    asSubclass.getClass();
                                    try {
                                        Constructor declaredConstructor = asSubclass.getDeclaredConstructor(null);
                                        declaredConstructor.setAccessible(true);
                                        try {
                                            Object newInstance = declaredConstructor.newInstance(null);
                                            newInstance.getClass();
                                            if (ps0Var instanceof w51) {
                                                LinkedHashMap linkedHashMap = ((cg) ((w51) ps0Var)).c().e;
                                                c4 b = ps0Var.b();
                                                Iterator it = new HashSet(linkedHashMap.keySet()).iterator();
                                                while (it.hasNext()) {
                                                    String str3 = (String) it.next();
                                                    str3.getClass();
                                                    s51 s51Var = (s51) linkedHashMap.get(str3);
                                                    if (s51Var != null) {
                                                        o30.g(s51Var, b, ps0Var.f());
                                                    }
                                                }
                                                if (!new HashSet(linkedHashMap.keySet()).isEmpty()) {
                                                    b.v();
                                                }
                                            } else {
                                                throw new IllegalStateException(("Internal error: OnRecreation should be registered only on components that implement ViewModelStoreOwner. Received owner: " + ps0Var).toString());
                                            }
                                        } catch (Exception e) {
                                            v7.j("Failed to instantiate ", str2, e);
                                            return;
                                        }
                                    } catch (NoSuchMethodException e2) {
                                        throw new IllegalStateException("Class " + asSubclass.getSimpleName() + " must have default constructor in order to be automatically recreated", e2);
                                    }
                                } catch (ClassNotFoundException e3) {
                                    throw new RuntimeException("Class " + str2 + " wasn't found", e3);
                                }
                            }
                            return;
                        }
                        v7.o("SavedState with restored state for the component \"androidx.savedstate.Restarter\" must contain list of strings by the key \"classes_to_restore\"");
                        return;
                    }
                    return;
                }
                throw new AssertionError("Next event must be ON_CREATE");
            case 1:
                MainActivity mainActivity = (MainActivity) obj;
                if (mainActivity.i == null) {
                    xf xfVar = (xf) mainActivity.getLastNonConfigurationInstance();
                    if (xfVar != null) {
                        mainActivity.i = xfVar.a;
                    }
                    if (mainActivity.i == null) {
                        mainActivity.i = new wb0(2);
                    }
                }
                mainActivity.e.f(this);
                return;
            case 2:
                new HashMap();
                hw[] hwVarArr = (hw[]) obj;
                if (hwVarArr.length <= 0) {
                    if (hwVarArr.length <= 0) {
                        return;
                    }
                    hw hwVar = hwVarArr[0];
                    throw null;
                }
                hw hwVar2 = hwVarArr[0];
                throw null;
            default:
                if (z70Var == z70.ON_CREATE) {
                    j80Var.f().f(this);
                    ((ks0) obj).b();
                    return;
                } else {
                    throw new IllegalStateException(("Next event must be ON_CREATE, it was " + z70Var).toString());
                }
        }
    }
}
