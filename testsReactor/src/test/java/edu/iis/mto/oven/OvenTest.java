package edu.iis.mto.oven;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    Oven oven;

    @Mock
    Fan fan;

    @Mock
    HeatingModule module;

    @BeforeEach
    void setUp(){
        oven = new Oven(module, fan);
    }

    @Test
    void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    void normalRunHeaterTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                        .withStageTime(100)
                        .withHeat(HeatType.HEATER)
                        .withTargetTemp(300)
                        .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunGrillTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.GRILL)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunThermoTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunWithTwoStagesTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.HEATER)
                .withTargetTemp(300)
                .build();
        ProgramStage stage2 = ProgramStage.builder()
                .withStageTime(250)
                .withHeat(HeatType.GRILL)
                .withTargetTemp(200)
                .build();
        stages.add(stage1);
        stages.add(stage2);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(0)
                .withStages(stages)
                .withCoolAtFinish(true)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void normalRunThermoNotCoolTest(){
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
    }

    @Test
    void verifySimpleRunHeaterTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100) //with 0 test also
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        Mockito.verify(module, Mockito.times(1)).heater(Mockito.any(HeatingSettings.class));
    }

    @Test
    void verifySimpleRunNoHeaterTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(0) //with 0 test also
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        Mockito.verify(module, Mockito.times(0)).heater(Mockito.any(HeatingSettings.class));
    }

    @Test
    void verifySimpleRunHeaterSettingsTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100) //with 0 test also
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                        .withTargetTemp(100)
                        .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                        .build();
        Mockito.verify(module, Mockito.times(1)).heater(settings);
    }

    @Test
    void verifySimpleRunHeaterSettingsFanOnTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100) //with 0 test also
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        Mockito.verify(module, Mockito.times(1)).heater(settings);
        Mockito.verify(fan).on();
        Mockito.verify(fan).off();
    }

    @Test
    void verifySimpleRunHeaterSettingsFanOffTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100) //with 0 test also
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        Mockito.verify(module, Mockito.times(1)).heater(settings);
        Mockito.verify(fan).off();
    }

    @Test
    void verifySimpleRunHeaterSettingsFanOnHeatingModuleTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        HeatingSettings settings300 = HeatingSettings.builder()
                .withTargetTemp(300)
                .withTimeInMinutes(100)
                .build();
        Mockito.verify(module, Mockito.times(1)).heater(settings);
        Mockito.verify(fan).on();
        Mockito.verify(module).termalCircuit(settings300);
        Mockito.verify(fan).off();
    }

    @Test
    void inOrderVerifySimpleRunHeaterSettingsFanOnHeatingModuleTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.THERMO_CIRCULATION)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        HeatingSettings settings300 = HeatingSettings.builder()
                .withTargetTemp(300)
                .withTimeInMinutes(100)
                .build();
        InOrder inOrder = Mockito.inOrder(module, fan);
        inOrder.verify(module, Mockito.times(1)).heater(settings);
        inOrder.verify(fan).on();
        inOrder.verify(module).termalCircuit(settings300);
        inOrder.verify(fan).off();
    }

    @Test
    void verifySimpleRunGrillSettingsFanOnHeatingModuleTest() throws HeatingException {
        List<ProgramStage> stages = new ArrayList<>();
        ProgramStage stage1 = ProgramStage.builder()
                .withStageTime(100)
                .withHeat(HeatType.GRILL)
                .withTargetTemp(300)
                .build();
        stages.add(stage1);
        BakingProgram bakingProgram = BakingProgram.builder()
                .withInitialTemp(100)
                .withStages(stages)
                .withCoolAtFinish(false)
                .build();
        oven.runProgram(bakingProgram);
        HeatingSettings settings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        HeatingSettings settings_grill = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(Oven.HEAT_UP_AND_FINISH_SETTING_TIME)
                .build();
        InOrder inOrder = Mockito.inOrder(module, fan);
        inOrder.verify(module, Mockito.times(1)).heater(settings);
        inOrder.verify(fan).isOn();
        inOrder.verify(module).grill(settings_grill);
        inOrder.verify(fan).off();
    }

}
