package lab3.beans;

import lab3.database.Repository;
import lab3.utils.FacesUtils;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

@Data
@ManagedBean(name = "hits")
@RequestScoped
public class HitsManager implements Serializable {
    private static final Float[] X_VALUES = {-4f, -3.5f, -3f, -2.5f, -2f, -1.5f, -1f, -0.5f, 0f, 0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f};
    private static final float MAX_Y = -5;
    private static final float MIN_Y = -5;
    private static final Integer[] R_VALUES = {1, 2, 3, 4, 5};

    @Inject
    private Repository<Hit> hitRepository;
    private List<Hit> hitBeansList;

    // Manual input
    private Float x;
    private Float y;

    // Canvas input
    private Float canvasX;
    private Float canvasY;
    private Float canvasR = 1f;

    @PostConstruct
    private void init() {
        hitBeansList = hitRepository.getAll();
    }

    public void clear() {
        hitBeansList.clear();
        hitRepository.clear();
    }

    private boolean validateManualInputs() {
        if (x == null) { // no X chosen
            FacesUtils.addFacesMessage(SEVERITY_ERROR, "Please select X coordinate");
            return false;
        }
        if (x<-4 || x>4) {
            FacesUtils.addFacesMessage(SEVERITY_ERROR, "X should be from -4 to 4");
            return false;
        }
        else if (!Arrays.asList(X_VALUES).contains(x)) {
            FacesUtils.addFacesMessage(SEVERITY_ERROR, "X should be from -4 to 4 with step 0.5");
            return false;
        }
        return true;
    }

    private boolean validateCanvasX() {
        return Arrays.asList(X_VALUES).contains(canvasX);
    }

    private void addHit(float x, float y, float r) {
        if (r==1 || r==2 || r==3 || r==4 || r==5) {
            Hit hit = new Hit(x, y, r);
            hitBeansList.add(hit);
            hitRepository.save(hit);
        }
        else {
            FacesUtils.addFacesMessage(SEVERITY_ERROR, "R should be integer from 1 to 5");
            return;
        }
    }

    public void submitManualInputHit(float radius) {
        if (!validateManualInputs()) return;
        addHit(x, y, radius);
    }

    public void submitCanvasClickHit() {
        if (!validateCanvasX()) return;
        addHit(canvasX, canvasY, canvasR);
    }
}
